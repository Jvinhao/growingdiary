package org.lf.diary.model.Vo;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/7 14:45
 * @Description: TODO
 */

@Entity
@Data
@Table(name = "activityVo")
public class ActivityVo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "act_id",length = 32)
    private String actId;

    @Column(name = "act_image")
    private String actImage;

    @Column(name = "act_video")
    private String actVideo;
}
