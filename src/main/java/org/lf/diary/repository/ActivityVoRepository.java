package org.lf.diary.repository;

import org.lf.diary.model.Vo.ActivityVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/20 8:44
 * @Description: TODO
 */
public interface ActivityVoRepository extends JpaRepository<ActivityVo,Long> {

    /**
     * 查找活动媒体
     * @param actId
     * @return
     */
    List<ActivityVo> findAllByActId(String actId);
}
