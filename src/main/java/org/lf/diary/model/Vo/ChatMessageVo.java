package org.lf.diary.model.Vo;

import lombok.Data;

/**
 * @Author: PengZH
 * @Date: 2020/3/11 14:46
 * @Description:
 */
@Data
public class ChatMessageVo {

    private Long id;

    //消息类型 1:文字 2:图片 3:emoji表情
    private Integer msgType;

    private String createTime;

    private String content;

    private Long fromUserId;

    private Long toUserId;

    //消息状态 0 未读  1已读
    private Byte status;

    private String fromUserImage;

    private String toUserImage;

}