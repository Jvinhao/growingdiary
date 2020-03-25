package org.lf.diary.service.Impl;

import org.lf.diary.core.ActivityEnum;
import org.lf.diary.model.Activity;
import org.lf.diary.model.ActivityComment;
import org.lf.diary.model.Vo.ActU;
import org.lf.diary.model.Vo.ActivityVo;
import org.lf.diary.repository.ActURepository;
import org.lf.diary.repository.ActivityCommentRepository;
import org.lf.diary.repository.ActivityRepository;
import org.lf.diary.repository.ActivityVoRepository;
import org.lf.diary.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/11 14:59
 * @Description: TODO
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActURepository actURepository;

    @Autowired
    private ActivityVoRepository activityVoRepository;

    @Autowired
    private ActivityCommentRepository activityCommentRepository;


    @Override
    public Boolean auditActivity(Activity activity) {
        activity.setActHot(10L);
        activity.setActPass(0);
        activity.setActState(ActivityEnum.活动未开始.getActState());
        activity.setActPeople(1L);
        activity.setCreateTime(new Date());
        Activity act = activityRepository.save(activity);

        ActU actU = new ActU();
        actU.setState(ActivityEnum.活动未开始.getActState());
        actU.setUserId(activity.getCreator());
        actU.setActId(act.getId());

        actURepository.save(actU);
        if (act != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<Activity> getAllActivity(Pageable pageable) {
        Page<Activity> activities = activityRepository.findAll(((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("actPass"), 1));

            query.where(predicates.toArray(new Predicate[]{}));

            query.orderBy(criteriaBuilder.desc(root.get("actStart")), criteriaBuilder.asc(root.get("actState")),
                    criteriaBuilder.asc(root.get("actHot"))
            );
            return null;
        }), pageable);

        for (Activity activity : activities) {
            List<ActivityVo> activityVos = activityVoRepository.findAllByActId(activity.getId());
            if(activityVos.size() != 0) {
                activity.setActCover(activityVos.get(0).getActImage());
            }else {
                activity.setActCover("/images/activity_default.jpg");
            }
        }
        return activities;
    }

    @Override
    public List<Activity> findAllActivity(Long id, Integer state) {

        List<ActU> uList = actURepository.findAll((root, query, cb) -> {
            //查询用户的所有活动
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("userId"), id));
            predicates.add(cb.equal(root.get("state"), state));
            query.where(predicates.toArray(new Predicate[]{}));
            return null;
        });
        List<Activity> activities = new ArrayList<>();
        uList.forEach((u) -> {
            Optional<Activity> activityOptional = activityRepository.findById(u.getActId());
            activityOptional.ifPresent(activities::add);
        });

        return activities;
    }

    @Override
    public Activity findActivity(String actId) {
        Optional<Activity> activityOptional = activityRepository.findById(actId);
        if (activityOptional.isPresent()) {
            Activity activity = activityOptional.get();
            List<ActU> actUList = actURepository.findAllByActId(actId);
            activity.setActUS(actUList);
            List<ActivityVo> activityVos = activityVoRepository.findAllByActId(actId);
            activity.setActivityVoList(activityVos);
            return activity;
        } else {
            throw new RuntimeException("此活动不存在!");
        }
    }

    @Override
    public List<ActivityComment> getAllComments(String actId) {
        List<ActivityComment> comments = activityCommentRepository.findAll((root, query, cb) -> {
            query.where(cb.equal(root.get("actId"), actId));
            query.orderBy(cb.desc(root.get("createTime")));
            return null;
        });
        return comments;
    }

    @Override
    public void actComment(String actId, String actContent, Long id) {
        ActivityComment activityComment = new ActivityComment();
        activityComment.setActId(actId);
        activityComment.setCreator(id);
        activityComment.setCommentContent(actContent);
        activityComment.setCreateTime(new Date());
        activityCommentRepository.save(activityComment);
    }

    @Override
    public void uploadActivityPhotos(String actId, String cover) {
        ActivityVo activityVo = new ActivityVo();
        activityVo.setActId(actId);
        activityVo.setActImage(cover);
        activityVoRepository.save(activityVo);
    }

    @Override
    public List<ActivityVo> getAllPhotos(String actId) {
        return activityVoRepository.findAllByActId(actId);
    }

    @Override
    public void joinActivity(String actId, Long id) {
        Optional<Activity> activityOptional = activityRepository.findById(actId);
        if (activityOptional.isPresent()) {
            Activity activity = activityOptional.get();
            Date date = new Date();
            ActU actU = new ActU();
            if (date.compareTo(activity.getActEnd()) < 0 && date.compareTo(activity.getActStart()) > 0) {
                actU.setActId(actId);
                actU.setUserId(id);
                actU.setState(ActivityEnum.活动进行中.getActState());
                actURepository.save(actU);
                activity.setActPeople(activity.getActPeople() + 1);
                activity.setActHot(activity.getActHot() + 10);
                activityRepository.save(activity);
            } else if (date.compareTo(activity.getActStart()) < 0) {
                actU.setActId(actId);
                actU.setUserId(id);
                actU.setState(ActivityEnum.活动未开始.getActState());
                actURepository.save(actU);
                activity.setActPeople(activity.getActPeople() + 1);
                activity.setActHot(activity.getActHot() + 10);
                activityRepository.save(activity);
            } else {
                actU.setActId(actId);
                actU.setUserId(id);
                actU.setState(ActivityEnum.活动已结束.getActState());
                actURepository.save(actU);
                activity.setActPeople(activity.getActPeople() + 1);
                activity.setActHot(activity.getActHot() + 10);
                activityRepository.save(activity);
            }
        }

    }
}
