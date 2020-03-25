package org.lf.diary.service;

/**
 * @Author: Jvinh
 * @DateTime: 2020/2/7 19:40
 * @Description: TODO
 */
public interface MailService {

    /**
     * 发送验证码
     * @param email
     * @return
     */
    String sendValidateCode(String email);

}
