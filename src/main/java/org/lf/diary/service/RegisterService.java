package org.lf.diary.service;

import org.lf.diary.model.User;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/20 21:40
 * @Description: TODO
 */
public interface RegisterService {

    /**
     * 处理注册功能
     * @param user
     * @return User
     */
    User doRegister(User user);

    /**
     * 检测用户名是否重复
     * @param username
     * @return
     */
    Boolean checkUserName(String username);

}
