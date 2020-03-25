package org.lf.diary.repository;

import org.lf.diary.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/7 14:54
 * @Description: TODO
 */
public interface ActivityRepository extends JpaRepository<Activity,String>, JpaSpecificationExecutor<Activity> {

}
