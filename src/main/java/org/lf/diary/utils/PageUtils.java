package org.lf.diary.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/12 17:45
 * @Description: TODO
 */
@Component
public class PageUtils {

    public Pageable getPageable(Pageable pageable) {
        if(pageable.getPageNumber() <= 0) {
            pageable = PageRequest.of(1,pageable.getPageSize(),pageable.getSort());
        }else {
            pageable = PageRequest.of(pageable.getPageNumber()-1,pageable.getPageSize(),pageable.getSort());
        }
        return pageable;
    }
}
