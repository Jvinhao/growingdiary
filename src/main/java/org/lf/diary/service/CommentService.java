package org.lf.diary.service;

import org.lf.diary.model.Comment;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/23 16:26
 * @Description: TODO
 */

public interface CommentService {

    /**
     * 添加一个评论
     * @param comment
     * @return
     */
    Comment addComment(Comment comment);

    /**
     * 查找某一日记的评论
     * @param diaryId
     * @return
     */
    List<Comment> list(Long diaryId);

    /**
     * 获取未读消息
     * @param id
     * @return
     */
    Long unRead(Long id);

    /**
     * 获取全部的评论
     * @param id
     * @return
     */
    List<Comment> getAllComment(Long id);

    /**
     * 修改是否已读评论
     * @param isRead
     * @param commentId
     */
    void updateCommentRead(Integer isRead, Long commentId);
}
