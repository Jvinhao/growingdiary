package org.lf.diary.controller.activity;

import org.lf.diary.core.ActivityEnum;
import org.lf.diary.core.Result;
import org.lf.diary.core.ResultGenerator;
import org.lf.diary.core.ServiceException;
import org.lf.diary.model.Activity;
import org.lf.diary.model.ActivityComment;
import org.lf.diary.model.User;
import org.lf.diary.model.Vo.ActivityVo;
import org.lf.diary.service.ActivityService;
import org.lf.diary.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/31 23:17
 * @Description: TODO
 */

@Controller
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private PageUtils pageUtils;

    @RequestMapping("/activity")
    public String activity(Model model, @PageableDefault(size = 10, page = 1) Pageable pageable) {
        pageable = pageUtils.getPageable(pageable);
        model.addAttribute("title", "activity");
        Page<Activity> activities = activityService.getAllActivity(pageable);
        model.addAttribute("activities", activities);
        return "activityList";
    }


    @RequestMapping("/activity/{actId}")
    public String activity(Model model, @PathVariable("actId") String actId) {
        model.addAttribute("title", "activity");
        Activity activity = activityService.findActivity(actId);
        model.addAttribute("activity", activity);
        List<ActivityComment> activityComments = activityService.getAllComments(actId);
        model.addAttribute("activityComments", activityComments);
        List<ActivityVo> activityVos = activityService.getAllPhotos(actId);
        model.addAttribute("activityVos", activityVos);
        return "activity_info";
    }

    @RequestMapping("/activity/actComment")
    @ResponseBody
    public Result actComment(HttpServletRequest request, @RequestParam("actId") String actId, @RequestParam("actContent") String actContent) {
        User user = (User) request.getSession().getAttribute("user");
        activityService.actComment(actId, actContent, user.getId());

        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(value = "/uploadActivityPhotos", method = RequestMethod.POST)
    public String uploadActivityPhotos(String actId, String activityPhoto) {
        activityService.uploadActivityPhotos(actId, activityPhoto);

        return "redirect:/activity/" + actId;
    }

    @RequestMapping(value = "/activity/joinActivity", method = RequestMethod.GET)
    @ResponseBody
    public Result joinActivity(String actId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        activityService.joinActivity(actId, user.getId());
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/home/activity")
    public String activity(Model model, HttpServletRequest request) {
        model.addAttribute("action", "7");
        model.addAttribute("title", "home");
        User user = (User) request.getSession().getAttribute("user");
        List<Activity> activities = activityService.findAllActivity(user.getId(), ActivityEnum.活动进行中.getActState());
        model.addAttribute("activities", activities);
        return "home/activity";
    }

    @RequestMapping("/createActivity")
    public String createActivity() {
        return "home/create_activity";
    }

    @RequestMapping(value = "/auditActivity", method = RequestMethod.POST)
    public String auditActivity(Activity activity, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new ServiceException("请先登录");
        } else {
            activity.setCreator(user.getId());
        }
        Boolean b = activityService.auditActivity(activity);
        if (b) {
            return "home/submit_activity";
        } else {
            throw new ServiceException("出错了！");
        }
    }

    @RequestMapping("/home/showActivity")
    @ResponseBody
    public Result showActivity(HttpServletRequest request, Integer state) {
        User user = (User) request.getSession().getAttribute("user");
        List<Activity> activities = activityService.findAllActivity(user.getId(), state);
        return ResultGenerator.genSuccessResult(activities);
    }


}
