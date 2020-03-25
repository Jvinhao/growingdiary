package org.lf.diary.controller.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.lf.diary.model.ChatMessage;
import org.lf.diary.model.Vo.ChatMessageVo;
import org.lf.diary.service.ChatMsgService;
import org.lf.diary.service.RelationShipService;
import org.lf.diary.service.UserService;
import org.lf.diary.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: PengZH
 * @Date: 2020/3/11 19:46
 * @Description:
 */
public class ChatWebSocketHandler extends TextWebSocketHandler {


    //不能自动注入这里,只能配置配置类注入静态成员变量
    public static ChatMsgService chatMsgService;
    public static UserService userService;
    public static RelationShipService shipService;
    //线程安全的HashMap用来存放用户id 和Session数据
    private  static final Map<Long,WebSocketSession> SESSIONS = new ConcurrentHashMap<Long, WebSocketSession>();

    private static final ObjectMapper MAPPER = new ObjectMapper();


    private static final String HELLO = "hello";
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {
        //心跳检测
        String check = message.getPayload();
        if(check.equals("ping")){
            session.sendMessage(new TextMessage(MAPPER.writeValueAsString(HELLO)));
            return;
        }

        //json解析数据
        JsonNode jsonNode = MAPPER.readTree(message.getPayload());
        Long toUser = jsonNode.get("toUser").asLong();
        Long from = jsonNode.get("fromUser").asLong();

        String msg = jsonNode.get("message").asText();


        Date date = new Date();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFromUserId(from);
        chatMessage.setToUserId(toUser);
        chatMessage.setMsgType(1);

        byte status = 0;
        chatMessage.setStatus(status);
        chatMessage.setCreateTime(date);
        chatMessage.setContent(msg);


        //1 拿到toUser转成Vo对象传输给前台
        String fromImg = userService.findById(from).getUserImg();
        String toImg = userService.findById(toUser).getUserImg();


        ChatMessageVo chatMessageVo  = new ChatMessageVo();
        BeanUtils.copyProperties(chatMessage,chatMessageVo);

        chatMessageVo.setCreateTime(DateUtils.getDateStr(date));
        chatMessageVo.setFromUserImage(fromImg);
        chatMessageVo.setToUserImage(toImg);

        //2.持久化
        chatMsgService.save(chatMessage);

        //3.若关系没有保存双向保存关系 不需要捕获异常
        try { shipService.save(from, toUser); }catch (Exception e){}
        try{ shipService.save(toUser,from);}catch (Exception e){}
        //3 判断是否在线
        WebSocketSession toSession = SESSIONS.get(toUser);


        if(toSession != null && toSession.isOpen()){
            //发送消息
            toSession.sendMessage(new TextMessage(MAPPER.writeValueAsString(chatMessageVo)));
        }

        //3.修改最后联系时间 触发器实现

    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws
            Exception {
        //连接后进行处理
        Long uid = (Long)session.getAttributes().get("uid");
        //讲当前用户的Session放入map中方便发送
        SESSIONS.put(uid,session);

    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {

    }
}