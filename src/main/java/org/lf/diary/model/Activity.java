package org.lf.diary.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.lf.diary.model.Vo.ActU;
import org.lf.diary.model.Vo.ActivityVo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/7 14:23
 * @Description: TODO
 */
@Data
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String id;

    @Column(name = "act_name" , length = 64)
    private String actName;

    @Column(name = "act_description",length = 256)
    private String actDescription;

    @Column(name = "act_start")
    private Date actStart;

    @Column(name = "act_end")
    private Date actEnd;

    @Column(name = "act_loc",length = 64)
    private String actLoc;

    @Column(name = "act_people")
    private Long actPeople;

    @Column(name = "act_hot")
    private Long actHot;

    @Column(name = "act_state")
    private Integer actState;

    @Column(name = "act_pass")
    private Integer actPass;

    @Column(name = "creator")
    private Long creator;

    @Column(name = "create_time")
    private Date createTime;

    @Transient
    private String actCover;

    @Transient
    private List<ActivityVo> activityVoList = new ArrayList<>();

    @Transient
    private List<ActU> actUS = new ArrayList<>();

}
