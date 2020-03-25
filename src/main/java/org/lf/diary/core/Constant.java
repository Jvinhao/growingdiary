package org.lf.diary.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/19 12:18
 * @Description: TODO
 */
@Component
public class Constant {

    @Value("${maxPage}")
    public Integer maxPage;

    @Value("${defaultImg}")
    public  String defaultImg;

    public Integer getMaxPage() {
        return maxPage;
    }

    public String getDefaultImg() {
        return defaultImg;
    }
}
