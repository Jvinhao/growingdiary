package org.lf.diary.service;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/27 18:35
 * @Description: TODO
 */
public interface IpRecordService {
    /**
     * 判断ip是否被封
     * @param ip
     * @return
     */
    Boolean findDisableIp(String ip);

    /**
     *
     * @param ipAddr
     * @param userId
     */
    void addInfo(String ipAddr, Long userId);
}
