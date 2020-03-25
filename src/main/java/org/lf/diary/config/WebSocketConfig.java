package org.lf.diary.config;

import org.lf.diary.controller.chat.ChatWebSocketHandler;
import org.lf.diary.interceptor.ChatWebSocketInterceptor;
import org.lf.diary.service.ChatMsgService;
import org.lf.diary.service.RelationShipService;
import org.lf.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Author: PengZH
 * @Date: 2020/3/11 19:43
 * @Description:
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChatWebSocketInterceptor chatWebSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(), "/wschat/{uid}").
                setAllowedOrigins("*").addInterceptors(chatWebSocketInterceptor);
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new ChatWebSocketHandler();
    }

    @Autowired
    public void setChatMsgService(ChatMsgService chatMsgService) {
        ChatWebSocketHandler.chatMsgService = chatMsgService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        ChatWebSocketHandler.userService = userService;
    }

    @Autowired
    public void setRelationShipService(RelationShipService shipService) {
        ChatWebSocketHandler.shipService = shipService;
    }
}

