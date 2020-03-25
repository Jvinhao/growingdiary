package org.lf.diary.repository;

import org.lf.diary.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/17 19:29
 * @Description: TODO
 */
@Repository
public interface DiaryRepository extends JpaRepository<Diary,Long>, JpaSpecificationExecutor<Diary> {

    /**
     * 更新点赞数
     * @param likeCount
     * @param id
     */
    @Query(value = "update diary set like_count = ?1 where id = ?2",nativeQuery = true)
    @Modifying
    void updatePraise(Integer likeCount, Long id);

    /**
     * 更新评论
     * @param diaryId
     */
    @Query(value = "update diary set comment_count = comment_count + 1 where id = ?1",nativeQuery = true)
    @Modifying
    void updateComment(Long diaryId);

    /**
     * 删除所有的日记本
     * @param id
     */
    void deleteAllByParentId(Long id);

    /**
     * 更新状态是否可见
     * @param isShow
     * @param id
     */
    @Query(value = "update diary set is_show = ?1 where id = ?2",nativeQuery = true)
    @Modifying
    void updateIsShow(Integer isShow, Long id);
}
