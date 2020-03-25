package org.lf.diary.repository;

import org.lf.diary.model.Authorize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/4 18:34
 * @Description: TODO
 */
@Repository
public interface AuthorizeRepository extends JpaRepository<Authorize,Long> {

    /**
     * 查找对应的userId
     * @param openId
     * @return
     */
    Optional<Authorize> findByOpenId(String openId);
}
