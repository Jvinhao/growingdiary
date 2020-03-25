package org.lf.diary.controller;

import org.lf.diary.model.Diary;
import org.lf.diary.service.DiaryService;
import org.lf.diary.service.UserService;
import org.lf.diary.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/14 14:03
 * @Description: TODO
 */
@Controller
public class IndexController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PageUtils pageUtils;

    private static String root = "/growing_diary/";

    @RequestMapping("/")
    public String index(Model model, @PageableDefault(size = 10,page = 1) Pageable pageable) {
        pageable = pageUtils.getPageable(pageable);
        Page<Diary> diaryList = diaryService.list(pageable);
        if(pageable.getPageNumber() > diaryList.getTotalPages()) {
            pageable = PageRequest.of(diaryList.getTotalPages()-1,pageable.getPageSize(),pageable.getSort());
            diaryList = diaryService.list(pageable);
        }
        model.addAttribute("userCount",userService.countUser());
        model.addAttribute("diaryCount",diaryService.countDiary());
        model.addAttribute("diaryList",diaryList);

        model.addAttribute("title","index");

        return "index";
    }

    @RequestMapping("/joinUs")
    public String joinUs() {
        return "join";
    }

}
