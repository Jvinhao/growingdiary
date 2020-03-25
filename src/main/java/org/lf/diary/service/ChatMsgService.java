package org.lf.diary.service;

import org.lf.diary.model.ChatMessage;
import org.lf.diary.model.RelationShip;
import org.lf.diary.model.Vo.ChatMessageVo;


import java.util.List;

/**
 * @Author: PengZH
 * @Date: 2020/3/7 19:18
 * @Description:
 */
public interface ChatMsgService {
    /**
     * 获取当前用户所有未读信息条数
     */

    Long countUnread(Long id);


    Long countUnreadByOne(Long host, Long contact);


    List<ChatMessageVo> findChatMessageByRelation(RelationShip relationShip);

    //单方面清除host 收到contator的未读消息
    void clearUnread(RelationShip r);

    void save(ChatMessage chatMessage);
}
