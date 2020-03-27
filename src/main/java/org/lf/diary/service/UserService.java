package org.lf.diary.service;

import org.lf.diary.core.AccessTokenDTO;
import org.lf.diary.model.User;
import org.lf.diary.model.Vo.SafeUserVo;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/21 22:37
 * @Description: TODO
 */
public interface UserService {

    /**
     * 更新用户头像
     * @param userImg
     * @param id
     */
    void updateUserImg(String userImg, Long id);

    /**
     * 保存用户
     * @param user
     * @return User
     */
    User saveUser(User user);

    /**
     * 统计用户
     * @return
     */
    Long countUser();

    /**
     * 找回密码
     * @param user
     * @param validateCode
     * @return
     */
    Boolean findPassword(User user, String validateCode);

    /**
     * 通过openId查询用户
     * @param accessTokenDTO
     * @return
     */
    User findUser(AccessTokenDTO accessTokenDTO);

    /**
     * 绑定用户
     * @param user
     * @param openId
     * @return
     */
    User bindUser(User user, String openId);

    /**
     * 快速注册
     * @param accessTokenDTO
     * @return User
     */
    User quickRegister(AccessTokenDTO accessTokenDTO);

    /**
     * @param :id
     * @return User
     */
    User findById(Long id);

    /**
     * @param :keyword
     * @Description:根据输入的关键字查找用户
     */
    List<SafeUserVo> searchFriend(String keyword);
}
