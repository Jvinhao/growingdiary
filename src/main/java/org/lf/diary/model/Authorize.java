package org.lf.diary.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/4 18:35
 * @Description: TODO
 */
@Data
@Entity
@Table(name = "authorize")
public class Authorize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "user_id")
    private Long userId;


    @OneToOne
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private User user;
}
