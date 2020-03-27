package org.lf.diary.controller.authentication;

import org.lf.diary.model.User;
import org.lf.diary.service.UserService;
import org.lf.diary.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.Jedis;

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

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/updateUserImg")
    public String updateUserImg(String userImg, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        userService.updateUserImg(userImg, user.getId());
        request.getSession().removeAttribute("user");
        Jedis jedis = redisUtil.getJedis();
        jedis.del(user.getToken());
        jedis.close();
        return "redirect:/home/self";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(User user, HttpServletRequest request) {
        User uo = (User) request.getSession().getAttribute("user");
        if (uo != null) {
            userService.saveUser(user);
            Jedis jedis = redisUtil.getJedis();
            jedis.del(user.getToken());
            jedis.close();
            request.getSession().removeAttribute("user");
        }

        return "redirect:/home/self";
    }
}
