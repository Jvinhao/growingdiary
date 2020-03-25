package org.lf.diary.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @Author: PengZH
 * @Date: 2020/3/11 21:15
 * @Description:
 */
@Component
public class ChatWebSocketInterceptor implements HandshakeInterceptor {
    /**
     * 握手之前，若返回false，则不建立链接
     * *
     @param request
      * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse
            response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String path = request.getURI().getPath();
        String[] ss = StringUtils.split(path, '/');
        if(ss.length != 2){
            return false;
        } if(!StringUtils.isNumeric(ss[1])){
            return false;
        }
        //拿到当前用户的名字
        attributes.put("uid", Long.valueOf(ss[1]));
        return true;
    }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse
            response, WebSocketHandler wsHandler, Exception exception) {

    }
}