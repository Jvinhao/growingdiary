package org.lf.diary.controller.authentication;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.lf.diary.core.Result;
import org.lf.diary.core.ResultGenerator;
import org.lf.diary.model.User;
import org.lf.diary.service.IpRecordService;
import org.lf.diary.service.RegisterService;
import org.lf.diary.utils.RedisUtil;
import org.lf.diary.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/19 14:29
 * @Description: TODO
 */

@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private IpRecordService ipRecordService;

    @RequestMapping("/register")
    public String register() {
        return "register";
    }


    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public String doRegister(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        String ipAddr = securityUtils.getIpAddr(request);
        if(StringUtils.isBlank(ipAddr)) {
            return "redirect:/error/disableIp";
        }
        User userDb = registerService.doRegister(user);
        if (userDb != null) {
            //添加ip
            ipRecordService.addInfo(ipAddr,userDb.getId());

            Jedis jedis = redisUtil.getJedis();
            request.getSession().setAttribute("user", userDb);
            String userToRedis = JSON.toJSONString(userDb);
            jedis.set(userDb.getToken(), userToRedis);
            jedis.expire(userDb.getToken(), 60 * 60 * 24);
            jedis.close();
            Cookie cookie = new Cookie("token", userDb.getToken());
            cookie.setMaxAge(7200);
            response.addCookie(cookie);
            return "redirect:/registerSuccess";
        } else {
            model.addAttribute("errMsg", "该昵称已存在,请换一个新的昵称哦!");
            return "register";
        }
    }

    @RequestMapping("/registerSuccess")
    public String registerSuccess() {
        return "registerSuccess";
    }


    @RequestMapping("/checkUserName")
    @ResponseBody
    public Result checkUserName(String username) {
        if ("".equals(username)) {
            return ResultGenerator.genFailResult("请输入昵称!");
        }
        Boolean b = registerService.checkUserName(username);
        if (b) {
            return ResultGenerator.genSuccessResult("恭喜此昵称可用哦!");
        } else {
            return ResultGenerator.genFailResult("改昵称已存在,请重新输入哦!");
        }
    }




}
