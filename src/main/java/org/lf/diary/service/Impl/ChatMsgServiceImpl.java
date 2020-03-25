package org.lf.diary.service.Impl;

import lombok.SneakyThrows;
import org.lf.diary.model.ChatMessage;
import org.lf.diary.model.RelationShip;
import org.lf.diary.model.User;
import org.lf.diary.model.Vo.ChatMessageVo;
import org.lf.diary.repository.ChatMessageRepository;
import org.lf.diary.service.ChatMsgService;
import org.lf.diary.service.UserService;
import org.lf.diary.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @Author: PengZH
 * @Date: 2020/3/7 19:18
 * @Description:
 */
@Service
public class ChatMsgServiceImpl implements ChatMsgService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserService userService;
    private final String SORT_MSG = "createTime";
    private final Integer getMsgBeforeDays = 30;
    //当前用户总未读消息数目
    @Override
    public Long countUnread(Long id) {
        return chatMessageRepository.countUnread(id);
    }

    private static final HashMap<Long, User> userHashMap  = new HashMap<Long,User>();

    /**
     * 获取当前用户和对应联系人未读的消息条数
     * example:  查找24号收到来自21号的未读消息条数
     * 传参 24 21
     * */
    @Override
    public Long countUnreadByOne(Long host, Long contact) {
        return chatMessageRepository.countUnreadByOne(host, contact);
    }

    /**
     * 按时间倒序加载一个月一组关系之间的聊天记录 (减轻数据库压力)
     * 若查询过，用redis进行消息缓存
     */
    @Override
    public List<ChatMessageVo> findChatMessageByRelation(RelationShip relationShip) {
        if (relationShip.getHostId().equals(relationShip.getContactId())) return new ArrayList<ChatMessageVo>();
        Sort sort = Sort.by(Sort.Direction.ASC, SORT_MSG);
        Specification<ChatMessage> specification = new Specification<ChatMessage>() {
            @SneakyThrows
            @Override
            public Predicate toPredicate(Root<ChatMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate one = cb.equal(root.get("fromUserId"), relationShip.getHostId());
                Predicate two = cb.equal(root.get("toUserId"),relationShip.getContactId());
                Predicate three = cb.and(one,two);

                Predicate four = cb.equal(root.get("fromUserId"), relationShip.getContactId());
                Predicate five = cb.equal(root.get("toUserId"),relationShip.getHostId());
                Predicate six = cb.and(four,five);

                Predicate p1 = cb.or(three, six);

                List<Date> dateBeforeKDays = DateUtils.getDateBeforeKDays(getMsgBeforeDays);
                Date now = dateBeforeKDays.get(0);
                Date before = dateBeforeKDays.get(1);
                Predicate pd = null;
                if(now!=null&&before!=null){
                    pd = cb.between(root.get("createTime"),before,now);
                }
                if(pd!=null){
                    return cb.and(pd,p1);
                }else{
                    return null;
                }
            }
        };
        List<ChatMessage> all = chatMessageRepository.findAll(specification, sort);


        List<ChatMessageVo> res = new LinkedList<>();
        for(ChatMessage cm: all){
            ChatMessageVo cq = new ChatMessageVo();
            Long fromUserId = cm.getFromUserId();
            Long toUserId = cm.getToUserId();
            //用hashmap进行缓存 如果hashmap有就不用查数据库了
            if(userHashMap.get(fromUserId)==null){
                userHashMap.put(fromUserId,userService.findById(fromUserId));
            }if(userHashMap.get(toUserId)==null){
                userHashMap.put(toUserId,userService.findById(toUserId));
            }

            cq.setFromUserImage(userHashMap.get(fromUserId).getUserImg());
            cq.setToUserImage(userHashMap.get(toUserId).getUserImg());

            BeanUtils.copyProperties(cm,cq);
            //日期转换成String类型的
            cq.setCreateTime(DateUtils.getDateStr(cm.getCreateTime()));
            res.add(cq);
        }
        return res;
    }

    @Override
    public void clearUnread(RelationShip r) {
        chatMessageRepository.clearUnread(r.getHostId(),r.getContactId());
    }

    @Override
    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }


}
