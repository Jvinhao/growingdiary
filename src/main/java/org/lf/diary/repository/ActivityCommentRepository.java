package org.lf.diary.repository;

import org.lf.diary.model.ActivityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/20 9:16
 * @Description: TODO
 */
public interface ActivityCommentRepository extends JpaRepository<ActivityComment,Long>, JpaSpecificationExecutor<ActivityComment> {

    /**
     * 查询评论
     * @param actId
     * @return
     */
    List<ActivityComment> findAllByActId(String actId);
}
