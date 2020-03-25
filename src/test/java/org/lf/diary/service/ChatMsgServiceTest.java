package org.lf.diary.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lf.diary.model.RelationShip;
import org.lf.diary.model.Vo.ChatMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: PengZH
 * @Date: 2020/3/8 23:15
 * @Description:
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ChatMsgServiceTest {

    @Autowired
     ChatMsgService chatMsgService ;
    @Test
    public void testfindChatMessageByRelation(){
        RelationShip r = new RelationShip();
        r.setHostId(21L);
        r.setContactId(24L);
        List<ChatMessageVo> chatMessageByRelation = chatMsgService.findChatMessageByRelation(r);
        System.out.println(chatMessageByRelation.size());
        for(ChatMessageVo chat:chatMessageByRelation){
            System.out.println(chat.getCreateTime());
            System.out.println(chat.getFromUserId());
            System.out.println(chat.getToUserId());
            System.out.println(chat.getContent());
        }

    }
    @Test
    public void countUnreadNum(){
        Long countUnread = chatMsgService.countUnread(21L);
        System.out.println(countUnread);
    }

}
