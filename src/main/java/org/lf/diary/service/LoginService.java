package org.lf.diary.service;

import org.lf.diary.model.User;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/14 14:51
 * @Description: TODO
 */

public interface LoginService {

    /**
     * 验证登录是否成功
     * @param user
     * @return boolean
     */

    User doLogin(User user);
}
