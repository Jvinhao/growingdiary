package org.lf.diary.repository;

import org.lf.diary.model.IpRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/27 18:30
 * @Description: TODO
 */
public interface IpRecordRepository extends JpaRepository<IpRecord,Long> {

    /**
     * 判断ip是否为黑名单
     * @param ip
     * @return
     */
    Optional<IpRecord> findByIp(String ip);


}
