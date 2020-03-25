package org.lf.diary.model.qo;

import lombok.Data;
import org.lf.diary.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: PengZH
 * @Date: 2020/3/9 21:38
 * @Description:
 */
@Data
public class RelationShipQo {
    //当前用户
    private User host;

    private User contactor;

    private Date lastTime;

    private Long unReadNum;

}
