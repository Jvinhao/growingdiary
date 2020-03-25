package org.lf.diary.controller.chat;


import org.lf.diary.config.DateConverterConfig;
import org.lf.diary.core.Result;
import org.lf.diary.core.ResultGenerator;
import org.lf.diary.model.RelationShip;
import org.lf.diary.model.User;
import org.lf.diary.model.Vo.ChatMessageVo;
import org.lf.diary.model.Vo.RelationShipVo;
import org.lf.diary.model.Vo.SafeUserVo;
import org.lf.diary.service.ChatMsgService;
import org.lf.diary.service.RelationShipService;
import org.lf.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatMsgService chatMsgService;

    @Autowired
    private RelationShipService shipService;

    @Autowired
    private UserService userService;

    @Autowired
    private DateConverterConfig dateConverterConfig;


    //点击右小角小标，加载全部联系人，及每个联系人未读的消息条数到页面中。
    @GetMapping("/getContactors")
    @ResponseBody
    public Result getContactors(@RequestParam(value = "userId",required = true) Long hostId){
        User host = userService.findById(hostId);
        List<RelationShip> RelationShips = shipService.findByHostId(hostId);

        //按已读和未读排一次序
        List<RelationShipVo> isReadList = new ArrayList<>();
        List<RelationShipVo> unReadList = new ArrayList<>();
        for(RelationShip rs : RelationShips){
            //当前联系人id
            Long contactId = rs.getContactId();
            //查找的user对象
            User contact = userService.findById(contactId);
            //查找的未读记录数量
            Long unread = chatMsgService.countUnreadByOne(rs.getHostId(), rs.getContactId());

            RelationShipVo shipQo = new RelationShipVo();
            shipQo.setContactor(contact);
            shipQo.setUnReadNum(unread);
            Date lastTime = rs.getLastTime();

            if(lastTime!=null) {
                shipQo.setLastTime(dateConverterConfig.convert(rs.getLastTime().toString()));
            }else{
                shipQo.setLastTime(new Date());
            }
            shipQo.setHost(host);
            if(unread==0) {
                unReadList.add(shipQo);
            } else {
                isReadList.add(shipQo);
            }
        }
        boolean b = isReadList.addAll(unReadList);
        //排序好的
        return ResultGenerator.genSuccessResult(isReadList);
    }


    /**
     * 查询一组关系的三十天聊天记录
     */
    @ResponseBody
    @GetMapping("/Message")
    public Result getMessage(@RequestParam(value = "hostId",required = true) Long hostId,
                             @RequestParam(value = "contatorId",required = true) Long contatorId){
        RelationShip r = new RelationShip();
        r.setHostId(hostId);
        r.setContactId(contatorId);
        List<ChatMessageVo> chatMessageByRelation = chatMsgService.findChatMessageByRelation(r);

        return ResultGenerator.genSuccessResult(chatMessageByRelation);
    }
    /**
     * 清零聊天记录
     */
    @ResponseBody
    @GetMapping("/clearUnread")
    public void clearUnReadNum(@RequestParam(value = "hostId",required = true) Long hostId,
                               @RequestParam(value = "contatorId",required = true) Long contatorId){
        RelationShip r = new RelationShip();
        r.setHostId(hostId);
        r.setContactId(contatorId);
        chatMsgService.clearUnread(r);
    }

    @GetMapping("/countUnreadNum")
    @ResponseBody
    public Result countUnreadNum(@RequestParam(value = "userId",required = true) Long userId ){
        Long countUnread = chatMsgService.countUnread(userId);

        return ResultGenerator.genSuccessResult(countUnread);
    }

    @GetMapping("/countUnreadNumOneToOne")
    @ResponseBody
    public Result countUnreadNumOneToOne(Long from,Long to){
        Long count = chatMsgService.countUnreadByOne(to, from);


        return ResultGenerator.genSuccessResult(count);

    }

    //模糊搜索好友

    @ResponseBody
    @GetMapping("/searchFriend")
    public Result searchFriend(String keyword ){
        List<SafeUserVo> userList = userService.searchFriend(keyword);

        if(userList==null||userList.isEmpty()){
            return ResultGenerator.genFailResult("not find");
        }
        return ResultGenerator.genSuccessResult(userList);
    }

    @ResponseBody
    @GetMapping("/checkIsRelation")
    public Result checkIsRelation(Long id1,Long id2){
        Boolean relationShip = shipService.findRelationShip(id1, id2);
        if(relationShip.equals(false)){
            return ResultGenerator.genFailResult("not find");
        }else{
            return ResultGenerator.genSuccessMsgResult("it's exist");
        }
    }

    @GetMapping("/saveRelationship")
    @ResponseBody
    public Result save(Long id1,Long id2){
        if(!id1.equals(id2)) {shipService.save(id1,id2);}
        return ResultGenerator.genSuccessMsgResult("ok");
    }
}
