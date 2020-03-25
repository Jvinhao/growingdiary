package org.lf.diary.controller.home;

import org.lf.diary.core.Result;
import org.lf.diary.core.ResultGenerator;
import org.lf.diary.model.DiaryBook;
import org.lf.diary.model.User;
import org.lf.diary.service.DiaryBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/16 17:44
 * @Description: TODO
 */
@Controller
public class DiaryBookController {

    @Autowired
    private DiaryBookService diaryBookService;

    @RequestMapping("/saveDiaryBook")
    public String saveDiaryBook(DiaryBook diaryBook, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        diaryBook.setCreator(user.getId());
        diaryBookService.saveDiaryBook(diaryBook);
        return "redirect:/home";
    }

    @RequestMapping("/delDiaryBook")
    @ResponseBody
    public Result delDiaryBook(Long id) {
        //删除日记本
        Boolean b = diaryBookService.delDiaryBook(id);
        if (b) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("此日记本不存在");
        }
    }
}
