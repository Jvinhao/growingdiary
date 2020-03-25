package org.lf.diary.repository;

import org.lf.diary.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/23 16:27
 * @Description: TODO
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>, JpaSpecificationExecutor<Comment> {

    /**
     * 通过 id 查找数据库
     * @param diaryId
     * @return
     */
    List<Comment> findAllByDiaryId(Long diaryId);


    /**
     * 统计未读数目
     * @param id
     * @return
     */
    @Query(value = "select count(1) from comment where creator = ?1 and is_read = 0",nativeQuery = true)
    Long countUnRead(Long id);

    /**
     * 修改已读状态
     * @param commentId
     */
    @Query(value = "update comment set is_read = 1 where id = ?1",nativeQuery = true)
    @Modifying
    void updateCommentRead(Long commentId);
}
