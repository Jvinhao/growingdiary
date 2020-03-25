package org.lf.diary.service.Impl;

import org.lf.diary.model.Comment;
import org.lf.diary.repository.CommentRepository;
import org.lf.diary.repository.DiaryRepository;
import org.lf.diary.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/23 16:26
 * @Description: TODO
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DiaryRepository diaryRepository;


    @Override
    @Transactional(rollbackOn = {Exception.class})
    public Comment addComment(Comment comment) {
        //添加评论
        comment.setCreateTime(new Date());
        comment.setModifyTime(new Date());
        comment.setIsRead(0);
        Comment commentDb = commentRepository.save(comment);
        //增加评论数
        diaryRepository.updateComment(comment.getDiaryId());

        return commentDb;
    }

    @Override
    public List<Comment> list(Long diaryId) {
        List<Comment> comments = commentRepository.findAllByDiaryId(diaryId);
        return comments;
    }

    @Override
    public Long unRead(Long id) {
        Long unRead = commentRepository.countUnRead(id);
        if(unRead == null) {
            return 0L;
        }else {
            return unRead;
        }
    }

    @Override
    public List<Comment> getAllComment(Long id) {

        List<Comment> commentList = commentRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("creator"), id));
            query.where(predicates.toArray(new Predicate[predicates.size()]));
            query.orderBy(criteriaBuilder.asc(root.get("isRead")),criteriaBuilder.desc(root.get("createTime")));
            return null;
        });

        return commentList;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateCommentRead(Integer isRead, Long commentId) {
        commentRepository.updateCommentRead(commentId);
    }
}
