package org.lf.diary.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/17 19:22
 * @Description: TODO
 */

@Data
@Entity
@Table(name = "diary")
public class Diary implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "diary_title")
    private String diaryTitle;
    @Column(name = "diary_content")
    private String diaryContent;
    @Column(name = "diary_date")
    private Date diaryDate;
    @Column(name = "creator")
    private Long creator;
    @Column(name = "comment_count")
    private Integer commentCount;
    @Column(name = "like_count")
    private Integer likeCount;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
    @Column(name = "is_show")
    private Integer isShow;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id",insertable = false,updatable = false)
    private DiaryBook diaryBook;



    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator",insertable = false,updatable = false)
    private User user;

}
