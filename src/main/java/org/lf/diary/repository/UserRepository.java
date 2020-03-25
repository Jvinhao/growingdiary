package org.lf.diary.repository;

import org.lf.diary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/14 14:56
 * @Description: TODO
 */

@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    User findByUsernameAndPassword(String username, String password);


    /**
     * 根据token 查询用户
     * @param token
     * @return user
     */
    User findByToken(String token);

    /**
     * 更新图片
     * @param userImg
     * @param id
     */


    @Query(value = "update user set user_img = ?1 where id = ?2",nativeQuery = true)
    @Modifying
    void updateUserImg(String userImg, Long id);


    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 统计用户名
     * @param username
     * @return
     */
    @Query(value = "select * from user where username = ?1",nativeQuery = true)
    List<User> countByUsername(String username);

    /**
     * 修改密码
     * @param password
     * @param email
     */
    @Query(value = "update user set password = ?1 where email = ?2",nativeQuery = true)
    @Modifying
    void updatePassword(String password, String email);

    /**
     * 通过邮箱查找用户
     * @param email
     * @return
     */
    List<User> findAllByEmail(String email);


    /**
     * 用户名模糊匹配首字母
     * @param
     */
    @Query(value = "select * from user where username like %?1%",nativeQuery = true)
    List<User> findByUsernameLike(String username);

    /**
     * 用户名模糊匹配首字母找出前7个
     * @param
     */
    @Query(value = "select * from user where username like %?1% LIMIT 0,5",nativeQuery = true)
    List<User> findByUsernameLikeTop5(String username);


}
