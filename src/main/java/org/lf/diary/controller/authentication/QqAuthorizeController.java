package org.lf.diary.controller.authentication;

import org.lf.diary.core.AccessTokenDTO;
import org.lf.diary.model.User;
import org.lf.diary.provider.QqProvider;
import org.lf.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/4 12:42
 * @Description: TODO
 */
@Controller
public class QqAuthorizeController {

    @Value("${client_id}")
    private String clientId;

    @Value("${client_secret}")
    private String clientSecret;

    @Value("${redirect_uri}")
    private String redirectUri;

    @Autowired
    private QqProvider qqProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("code") String code,
                            @RequestParam("state") String state, HttpServletRequest request, HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClientId(clientId);
        accessTokenDTO.setRedirectUri(redirectUri);
        accessTokenDTO.setClientSecret(clientSecret);
        String accessToken = qqProvider.getAccessToken(accessTokenDTO);
        accessTokenDTO.setAccessToken(accessToken);
        String openId = qqProvider.getOpenId(accessToken);
        accessTokenDTO.setOpenId(openId);
        User user = userService.findUser(accessTokenDTO);
        if (user == null) {
            request.getSession().setAttribute("accessTokenDTO", accessTokenDTO);
            return "bind";
        } else {
            request.getSession().setAttribute("user",user);
            Cookie cookie = new Cookie("token",user.getToken());
            cookie.setMaxAge(7300);
            response.addCookie(cookie);
            return "redirect:/";
        }

    }

    @RequestMapping("/bind")
    public String bind() {
        return "bind";
    }

    @RequestMapping(value = "/bindUser", method = RequestMethod.POST)
    public String bindUser(User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = (AccessTokenDTO) request.getSession().getAttribute("accessTokenDTO");
        User userBind = userService.bindUser(user, accessTokenDTO.getOpenId());
        request.getSession().removeAttribute("openId");
        if (userBind != null) {
            request.getSession().setAttribute("user", userBind);
            Cookie cookie = new Cookie("token", userBind.getToken());
            cookie.setMaxAge(7200);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            model.addAttribute("errMsg", "该用户不存在");
            return "bind";
        }
    }

    /**
     * 快速注册
     *
     * @return
     */
    @RequestMapping("/quickRegister")
    public String quickRegister(Model model, HttpServletRequest request, HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = (AccessTokenDTO) request.getSession().getAttribute("accessTokenDTO");
        User user = userService.quickRegister(accessTokenDTO);
        if (user != null) {
            request.getSession().setAttribute("user", user);
            Cookie cookie = new Cookie("token", user.getToken());
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            model.addAttribute("errMsg", "注册失败");
            return "bind";
        }

    }
}
