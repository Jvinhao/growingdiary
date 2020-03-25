package org.lf.diary.controller.authentication;

import org.lf.diary.model.User;
import org.lf.diary.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/14 14:19
 * @Description: TODO
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public String doLogin(User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        User userDb = loginService.doLogin(user);
        if(userDb == null) {
            model.addAttribute("errorMsg","用户名或者密码错误,请重新登录");
            return "login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user",userDb);
        Cookie cookie = new Cookie("token",userDb.getToken());
        cookie.setMaxAge(7200);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @RequestMapping("/login?logout")
    public String logout(Model model,HttpServletRequest request,HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
