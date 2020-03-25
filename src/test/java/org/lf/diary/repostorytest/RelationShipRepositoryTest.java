package org.lf.diary.repostorytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lf.diary.model.RelationShip;
import org.lf.diary.repository.RelationShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: PengZH
 * @Date: 2020/3/7 19:35
 * @Description:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RelationShipRepositoryTest {

    @Autowired
    private RelationShipRepository repository;

    @Test
    public void testFindByHostId(){

        List<RelationShip> byHostId = repository.findByHostId(21L);
        for(RelationShip rs:byHostId){
            System.out.println(rs.getHostId());
            System.out.println(rs.getContactId());
        }
    }

    @Test
    public void testSave(){
        RelationShip one = new RelationShip();
        one.setContactId(21L);
        one.setHostId(26L);
        repository.save(one);
    }

}
