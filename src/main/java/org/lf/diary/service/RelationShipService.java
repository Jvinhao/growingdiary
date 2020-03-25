package org.lf.diary.service;

import org.lf.diary.model.RelationShip;

import java.util.List;

/**
 * @Author: PengZH
 * @Date: 2020/3/7 19:16
 * @Description:
 */
public interface RelationShipService  {

    //根据HostID查找所有contactors
    List<RelationShip> findByHostId(Long hostId);

    //关系双向保存联系人
    void save(Long id1, Long id2);


    //查找一段关系
    Boolean findRelationShip(Long id1, Long id2);

}
