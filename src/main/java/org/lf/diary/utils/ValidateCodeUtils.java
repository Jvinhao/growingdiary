package org.lf.diary.utils;

import java.util.Random;

/**
 * @Author: Jvinh
 * @DateTime: 2020/2/7 20:24
 * @Description: 验证码工具类
 */

public class ValidateCodeUtils {

    public static String createCode() {

        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < 6; i++) {
            int n = random.nextInt(10);
            sb.append(n);
        }
        return sb.toString();
    }

}
