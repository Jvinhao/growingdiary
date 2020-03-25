package org.lf.diary.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: PengZH
 * @Date: 2020/3/6 18:59
 * @Description:
 */
@Data
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //消息类型 1:文字 2:图片 3:emoji表情
    @Column(name = "msg_type")
    private Integer msgType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(columnDefinition ="TEXT")
    private String content;

    @Column(name = "from_user_id")
    private Long fromUserId;

    @Column(name = "to_user_id")
    private Long toUserId;

    //消息状态 0 未读  1已读
    @Column(name = "msg_status")
    private Byte status;

}
