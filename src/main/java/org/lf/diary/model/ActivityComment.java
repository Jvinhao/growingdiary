package org.lf.diary.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/20 9:11
 * @Description: TODO
 */
@Data
@Entity
@Table(name = "activity_comment")
public class ActivityComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "act_id")
    private String actId;

    @Column(name = "comment_content")
    private String commentContent;

    @Column(name = "creator")
    private Long creator;

    @Column(name = "create_time")
    private Date createTime;

    @OneToOne
    @JoinColumn(name = "creator",insertable = false,updatable = false)
    private User user;

}
