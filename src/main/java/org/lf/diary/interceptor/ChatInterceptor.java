package org.lf.diary.interceptor;

import org.lf.diary.model.User;
import org.lf.diary.service.ChatMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: PengZH
 * @Date: 2020/3/9 19:43
 * @Description:
 */

@Component
public class ChatInterceptor implements HandlerInterceptor {

    @Autowired
    private ChatMsgService chatMsgService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("chatUnreadNum")!=null) {
            return true;
        }
        //判断是否登录
        User user = (User)request.getSession().getAttribute("user");
        //没有登录放行
        if(user==null) {
            return true;
        } else{
            //登陆后User对象会保存在Session中
            //TODO
            Long chatUnreadNum = chatMsgService.countUnread(user.getId());

            request.getSession().setAttribute("chatUnreadNum",chatUnreadNum);
            return true;
        }
    }
}
