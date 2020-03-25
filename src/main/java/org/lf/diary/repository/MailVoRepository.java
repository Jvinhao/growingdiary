package org.lf.diary.repository;

import org.lf.diary.core.MailVo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Jvinh
 * @DateTime: 2020/2/7 22:15
 * @Description: TODO
 */
public interface MailVoRepository extends JpaRepository<MailVo,Long> {

    /**
     * 查找是否有此条记录
     * @param validateCode
     * @return
     */
    MailVo findByValidateCode(String validateCode);
}
