package org.lf.diary.controller.home;

import org.lf.diary.core.Result;
import org.lf.diary.core.ResultGenerator;
import org.lf.diary.model.Comment;
import org.lf.diary.model.Diary;
import org.lf.diary.model.User;
import org.lf.diary.service.CommentService;
import org.lf.diary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/17 19:33
 * @Description: TODO
 */

@Controller
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private CommentService commentService;
    @RequestMapping("/home/diaryList")
    public String diaryList(Model model,HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        List<Diary> diaryList = diaryService.getDiaryList(user.getId());
        List<Comment> commentList = commentService.getAllComment(user.getId());
        model.addAttribute("commentList",commentList);
        model.addAttribute("diaryList",diaryList);
        model.addAttribute("action","2");
        model.addAttribute("title", "home");
        return "home/diaryList";
    }

    @RequestMapping(value = "/saveDiary",method = RequestMethod.POST)
    public String saveDiary(Diary diary, HttpServletRequest request) {

        User user = (User)request.getSession().getAttribute("user");
        if(user != null) {
            diary.setCreator(user.getId());
            diaryService.saveDiary(diary);
            return "redirect:/home/diaryList";
        }else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/delDiary",method = RequestMethod.POST)
    @ResponseBody
    public Result delDiary(Long id) {
        commentService.delAllComment(id);
        diaryService.delDiary(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(value = "/updatePraise",method = RequestMethod.POST)
    @ResponseBody
    public Result updatePraise(Integer likeCount,Long id) {
        diaryService.updatePraise(likeCount,id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(value = "/changeState")
    @ResponseBody
    public Result changeState(Integer isShow,Long id) {
        diaryService.changeState(isShow,id);
        return ResultGenerator.genSuccessResult();
    }
}
