package org.lf.diary.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.lf.diary.model.User;
import org.lf.diary.repository.UserRepository;
import org.lf.diary.service.CommentService;
import org.lf.diary.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/15 15:27
 * @Description: TODO
 */

@Component
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            Jedis jedis = redisUtil.getJedis();
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    String userStr = jedis.get(token);
                    User user;
                    if (StringUtils.isNotBlank(userStr)) {
                        user = JSON.parseObject(userStr, User.class);
                    } else {
                        user = userRepository.findByToken(token);
                    }
                    if (user != null) {
                        Long unReadNum;
                        request.getSession().setAttribute("user", user);
                        //获取用户当前未读的消息条数
                        String unRead = jedis.get("unRead" + user.getId());
                        if (StringUtils.isNotBlank(unRead)) {
                            unReadNum = JSON.parseObject(unRead, Long.class);
                        } else {
                            unReadNum = commentService.unRead(user.getId());
                            jedis.set("unRead" + user.getId(), unReadNum.toString());
                            jedis.expire("unRead" + user.getId(), 30);

                        }
                        request.getSession().setAttribute("unRead", unReadNum);
                    }
                }
            }
            redisUtil.close(jedis);
        }
        return true;
    }
}
