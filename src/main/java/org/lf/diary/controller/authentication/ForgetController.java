package org.lf.diary.controller.authentication;

import org.apache.commons.lang3.StringUtils;
import org.lf.diary.core.Result;
import org.lf.diary.core.ResultGenerator;
import org.lf.diary.model.User;
import org.lf.diary.service.MailService;
import org.lf.diary.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/20 20:54
 * @Description: TODO
 */
@Controller
public class ForgetController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;


    @RequestMapping("/forget")
    public String forget() {
        return "forget";
    }

    @RequestMapping("/forget/getValidateCode")
    @ResponseBody
    public Result getValidateCode(String email) {
        mailService.sendValidateCode(email);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/forget/updatePassword")
    public String updatePassword(String validateCode, User user, Model model) {
        if(StringUtils.isNotBlank(validateCode)) {
            Boolean b = userService.findPassword(user, validateCode);
            if(b) {
                model.addAttribute("successInfo","修改密码成功 ");
                return "success";
            }else {
                model.addAttribute("errorMsg","验证码出错!");
                return "forget";
            }
        }else {
            model.addAttribute("errorMsg","请输入验证码!");
            return "forget";
        }
    }
}
