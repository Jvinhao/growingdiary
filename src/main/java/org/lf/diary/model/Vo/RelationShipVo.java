package org.lf.diary.model.Vo;

import lombok.Data;
import org.lf.diary.model.User;

import java.util.Date;

/**
 * @Author: PengZH
 * @Date: 2020/3/11 14:46
 * @Description:
 */
@Data
public class RelationShipVo {
    //当前用户
    private User host;

    private User contactor;

    private Date lastTime;

    private Long unReadNum;

}