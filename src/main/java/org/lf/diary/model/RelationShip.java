package org.lf.diary.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengZH
 * @Date: 2020/3/6 18:22
 * @Description: 通讯关系表
 */
@Data
@Entity
@Table(name = "relation_ship")
public class RelationShip {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*注:使用的Id号都是User表中的UserId*/
    //当前用户号Id号
    @Column(name = "host")
    private Long hostId;

    //当前用户的联系人Id号
    @Column(name = "contact")
    private Long contactId;

    //最后一次联系的时间
    @Column(name = "last_time")
    private Date lastTime;

}
