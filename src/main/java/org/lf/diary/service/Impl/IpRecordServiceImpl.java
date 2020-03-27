package org.lf.diary.service.Impl;

import org.lf.diary.model.IpRecord;
import org.lf.diary.repository.IpRecordRepository;
import org.lf.diary.service.IpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/27 18:35
 * @Description: TODO
 */
@Service
public class IpRecordServiceImpl implements IpRecordService {

    @Autowired
    private IpRecordRepository ipRecordRepository;

    @Override
    public Boolean findDisableIp(String ip) {
        Optional<IpRecord> ipRecordOptional = ipRecordRepository.findByIp(ip);
        if (ipRecordOptional.isPresent()) {
            IpRecord ipRecord = ipRecordOptional.get();
            if (ipRecord.getAdmit() == 0) {
                return true;
            }else {
                String[] split = ipRecord.getUserIds().split("::");
                if(split.length == 3 || ipRecord.getCount() == 3) {

                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void addInfo(String ipAddr, Long userId) {
        Optional<IpRecord> ipRecordOptional = ipRecordRepository.findByIp(ipAddr);
        if(ipRecordOptional.isPresent()) {
            IpRecord ipRecord = ipRecordOptional.get();
            ipRecord.setCount(ipRecord.getCount() + 1);
            if(ipRecord.getCount() == 3) {
                ipRecord.setAdmit(0);
            }
            ipRecord.setUserIds(ipRecord.getUserIds() + "::" + userId);
            ipRecordRepository.save(ipRecord);
        }else {
            IpRecord ipRecord = new IpRecord();
            ipRecord.setIp(ipAddr);
            ipRecord.setAdmit(1);
            ipRecord.setUserIds(String.valueOf(userId));
            ipRecord.setCount(1L);
            ipRecordRepository.save(ipRecord);
        }
    }
}
