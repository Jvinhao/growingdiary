package org.lf.diary.repository;

import org.lf.diary.model.Vo.ActU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/10 11:10
 * @Description: TODO
 */
@Repository
public interface ActURepository extends JpaRepository<ActU,String>, JpaSpecificationExecutor<ActU> {

    /**
     * 查找活动媒体
     * @param actId
     * @return
     */
    List<ActU> findAllByActId(String actId);
}
