package org.lf.diary.service;

import org.lf.diary.model.Activity;
import org.lf.diary.model.ActivityComment;
import org.lf.diary.model.Vo.ActivityVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/11 14:58
 * @Description: TODO
 */
public interface ActivityService {
    /**
     * 审核活动
     * @param activity
     * @return
     */
    Boolean auditActivity(Activity activity);

    /**
     * 获取所有的活动
     * @param pageable
     * @return
     */
    Page<Activity> getAllActivity(Pageable pageable);

    /**
     * 获取未进行的活动
     * @param id
     * @param actState
     * @return
     */
    List<Activity> findAllActivity(Long id, Integer actState);

    /**
     * 查找活动
     * @param actId
     * @return
     */
    Activity findActivity(String actId);

    /**
     * 获取到所有的活动评论
     * @param actId
     * @return
     */
    List<ActivityComment> getAllComments(String actId);

    /**
     * 添加活动评论
     * @param actId
     * @param actContent
     * @param id
     */
    void actComment(String actId, String actContent, Long id);

    /**
     * 上传活动照片
     * @param actId
     * @param cover
     */
    void uploadActivityPhotos(String actId, String cover);

    /**
     * 获取到所有的活动
     * @param actId
     * @return
     */
    List<ActivityVo> getAllPhotos(String actId);

    /**
     * 报名参加活动
     * @param actId
     * @param id
     */
    void joinActivity(String actId, Long id);
}
