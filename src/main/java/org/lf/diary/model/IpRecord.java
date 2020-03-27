package org.lf.diary.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/27 18:21
 * @Description: TODO
 */
@Data
@Entity
@Table(name = "ip_record")
public class IpRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "count")
    private Long count;

    @Column(name = "user_ids")
    private String userIds;

    @Column(name = "admit")
    private Integer admit;
}
