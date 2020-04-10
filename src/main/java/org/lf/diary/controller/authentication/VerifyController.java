package org.lf.diary.controller.authentication;

import org.lf.diary.utils.ValidateCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 获取验证信息
 */
@Controller
@RequestMapping("/verify")
public class VerifyController {

    /**
     * 获取验证码图片
     *
     * @param request
     * @param response
     */
    @GetMapping("codeImg")
    @ResponseBody
    public void getVerifyImg(HttpServletRequest request, HttpServletResponse response) {

        //获取4位数验证码
        String verifyCode = ValidateCodeUtils.createCode(4);
        //根据验证码生成图片
        BufferedImage image = ValidateCodeUtils.createVerifyCodeImg(verifyCode);

        response.setContentType("image/jpeg");
        // 禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession();
        //验证码保存到Session
        session.setAttribute("verifyCode", verifyCode);
        try {
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
