package org.lf.diary.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/14 14:44
 * @Description: TODO
 */

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private int sex;
    private String email;

    @Column(name = "user_img")
    private String userImg;
    private String school;
    private String hobbies;
    private String bio;

    private String token;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;


}
