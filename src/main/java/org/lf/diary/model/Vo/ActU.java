package org.lf.diary.model.Vo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.lf.diary.model.User;

import javax.persistence.*;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/10 10:50
 * @Description: TODO
 */
@Data
@Entity
@Table(name = "act_u")
public class ActU {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    @Column(name = "act_id")
    private String actId;
    @Column(name = "user_id")
    private Long userId;
    private Integer state;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user;


}
