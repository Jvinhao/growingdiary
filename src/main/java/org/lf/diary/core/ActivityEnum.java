package org.lf.diary.core;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/11 11:09
 * @Description: TODO
 */
public enum  ActivityEnum {
    /**
     *
     */
    活动已结束(1,"活动已结束"),
    活动进行中(0,"活动进行中"),
    活动未开始(-1,"活动未开始");
    private Integer actState;
    private String actStateDes;

    ActivityEnum(Integer actState, String actStateDes) {
        this.actState = actState;
        this.actStateDes = actStateDes;
    }

    public Integer getActState() {
        return actState;
    }

    public void setActState(Integer actState) {
        this.actState = actState;
    }

    public String getActStateDes() {
        return actStateDes;
    }

    public void setActStateDes(String actStateDes) {
        this.actStateDes = actStateDes;
    }}
