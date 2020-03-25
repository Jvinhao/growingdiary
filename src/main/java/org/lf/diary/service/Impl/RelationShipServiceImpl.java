package org.lf.diary.service.Impl;

import org.lf.diary.model.RelationShip;
import org.lf.diary.repository.RelationShipRepository;
import org.lf.diary.service.RelationShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengZH
 * @Date: 2020/3/7 19:17
 * @Description:
 */

@Service
public class RelationShipServiceImpl implements RelationShipService {

    @Autowired
    private RelationShipRepository relationShipRepository;

    //根据HostID查找所有contactors
    @Override
    public List<RelationShip> findByHostId(Long hostId) {
        //设置排序方式:按最后一次联络的时间倒序排列
        Sort sort = Sort.by(Sort.Direction.DESC,"lastTime");

        Specification<RelationShip> specification = new Specification<RelationShip>() {
            @Override
            public Predicate toPredicate(Root<RelationShip> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p  = cb.equal(root.get("hostId"),hostId);
                return p;
            }
        };

        return relationShipRepository.findAll(specification,sort);
    }

    //保存一对关系
    @Override
    public void save(Long hostId,Long ContatorId) {
        Boolean res = this.findRelationShip(hostId, ContatorId);
        if(res.equals(false)) {
            RelationShip relationShip = new RelationShip();
            relationShip.setHostId(hostId);
            relationShip.setContactId(ContatorId);
            relationShip.setLastTime(new Date());
            relationShipRepository.save(relationShip);
        }
    }

    @Override
    public Boolean findRelationShip(Long id1, Long id2) {
        List<RelationShip> relationShip = relationShipRepository.findRelationShip(id1, id2);
        if(relationShip==null||relationShip.isEmpty()){
            return false;
        }
        return true;
    }

}

