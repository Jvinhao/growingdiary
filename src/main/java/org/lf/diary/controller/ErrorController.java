package org.lf.diary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Jvinh
 * @DateTime: 2020/2/22 13:33
 * @Description: TODO
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    /**
     * 404页面
     */
    @GetMapping(value = "/404")
    public String error404() {
        return "error/error_404";
    }

    /**
     * 500页面
     */
    @GetMapping(value = "/500")
    public String error500() {
        return "error/error_500";
    }

}