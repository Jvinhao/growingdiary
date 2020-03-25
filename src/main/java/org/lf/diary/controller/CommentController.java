package org.lf.diary.controller;

import org.lf.diary.core.Result;
import org.lf.diary.core.ResultGenerator;
import org.lf.diary.model.Comment;
import org.lf.diary.model.User;
import org.lf.diary.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/23 15:56
 * @Description: TODO
 */

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    @RequestMapping("/showComment")
    @ResponseBody
    public Result showComment(Long diaryId) {
        List<Comment> comments = commentService.list(diaryId);
        return ResultGenerator.genSuccessResult(comments);
    }

    @RequestMapping("/doComment")
    @ResponseBody
    public Result doComment(Comment comment, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        comment.setReviewer(user.getId());
        Comment commentDb = commentService.addComment(comment);
        return ResultGenerator.genSuccessResult(commentDb);
    }

    @RequestMapping("/updateCommentRead")
    @ResponseBody
    public Result updateCommentRead(Integer isRead,Long commentId) {
        if(isRead == 0) {
            commentService.updateCommentRead(isRead,commentId);
            return ResultGenerator.genSuccessResult("更新状态成功");
        }else {
            return ResultGenerator.genFailResult("当前评论状态 已经改变");
        }

    }
}
