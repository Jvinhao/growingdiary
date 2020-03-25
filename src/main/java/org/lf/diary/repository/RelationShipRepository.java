package org.lf.diary.repository;

import org.lf.diary.model.RelationShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: PengZH
 * @Date: 2020/3/7 19:14
 * @Description:
 */
public interface RelationShipRepository extends JpaRepository<RelationShip,Long>, JpaSpecificationExecutor<RelationShip> {

    //根据HostID查找所有contactors
    List<RelationShip>  findByHostId(Long hostId);



    /**
     * save:方法 contact和host两个不能与数据库中的重复(数据库索引实现了)
     * 双向保存
     * @param id1
     * @param id2
     */
    //修改最后联系时间 用触发器完成


    @Query(value = "select * from  relation_ship where host = ?1 and contact = ?2",nativeQuery = true)
    List<RelationShip> findRelationShip(Long id1, Long id2);
}

