package org.lf.diary.repostorytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lf.diary.model.RelationShip;
import org.lf.diary.repository.ChatMessageRepository;
import org.lf.diary.repository.RelationShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: PengZH
 * @Date: 2020/3/7 21:04
 * @Description:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ChatMessageRepositoryTest {

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private RelationShipRepository relationShipRepository;

    //加载一个人的所有联系人，和每个联系人未读的消息数目
    @Test
    public void countUnreadByOne(){
        Long id = 24L;
        List<RelationShip> byHostId = relationShipRepository.findByHostId(id);
        for(RelationShip relation: byHostId){
            Long number = chatMessageRepository.countUnreadByOne(relation.getHostId(), relation.getContactId());
            System.out.println(relation.getHostId()+"收到"+relation.getContactId()+"未读的消息有"+number+"条");
        }
    }
    @Test
    public void testUnread(){
        Long id = 24L;
        Long aLong = chatMessageRepository.countUnread(id);
        System.out.println(aLong);
    }
}
