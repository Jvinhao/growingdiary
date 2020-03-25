package org.lf.diary.controller.authentication;

import org.lf.diary.model.User;
import org.lf.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/21 22:38
 * @Description: TODO
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/updateUserImg")
    public String updateUserImg(String userImg, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        userService.updateUserImg(userImg,user.getId());
        return "redirect:/self";
    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public String updateUser(User user) {
        userService.saveUser(user);
        return "redirect:/self";
    }
}
