package org.lf.diary.core;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Jvinh
 * @DateTime: 2020/2/7 20:11
 * @Description: TODO
 */

@Data
@Entity
@Table(name = "mail_validate")
public class MailVo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "from_user")
    private String fromUser;
    @Column(name = "to_user")
    private String toUser;
    @Column(name = "mail_sub")
    private String mailSub;
    @Column(name = "validate_code")
    private String validateCode;
    @Column(name = "send_date")
    private Date sentDate;
    @Column(name = "time_out")
    private Date timeOut;
}

