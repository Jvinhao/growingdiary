package org.lf.diary.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/15 16:45
 * @Description: TODO
 */
@Data
@Entity
@Table(name = "diary_book")
public class DiaryBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Column(name = "cover")
    private String cover;
    private Long creator;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;

}
