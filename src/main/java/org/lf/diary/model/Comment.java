package org.lf.diary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/23 15:47
 * @Description: TODO
 */
@Data
@Entity
@Table(name = "comment")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "diary_id")
    private Long diaryId;

    private String content;
    /**
     * 发表评论的人 -> 通过此id 查找到发表评论的对象
     */
    private Long reviewer;

    /**
     * 写日记的人 -> 通过此id 查找被评论的对象
     */
    private Long creator;
    @Column(name = "is_read")
    private Integer isRead;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
    /**
     * 级联查找 不更新 不插入
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewer",insertable = false,updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "diary_id",insertable = false,updatable = false)
    private Diary diary;
}
