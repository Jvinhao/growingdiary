package org.lf.diary.core;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/20 21:34
 * @Description: TODO
 */

public enum SexJudge {
    /**
     *
     */
    boy(1, "男"),
    girl(2, "女");
    private int key;
    private String value;

    SexJudge(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static int judgeGender(String gender) {
        if(StringUtils.equals(gender,SexJudge.boy.value)) {
            return 1;
        }else {
            return 2;
        }
    }




}
