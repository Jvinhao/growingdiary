package org.lf.diary.model.Vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: PengZH
 * @Date: 2020/3/12 17:31
 * @Description:
 */
@Data
public class SafeUserVo {

    private Long id;

    private String username;

    private String userImg;

    private Date createTime;

    private Date modifyTime;

}
