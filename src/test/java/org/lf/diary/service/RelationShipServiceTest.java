package org.lf.diary.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lf.diary.config.DateConverterConfig;
import org.lf.diary.model.RelationShip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengZH
 * @Date: 2020/3/8 21:49
 * @Description:
 */


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RelationShipServiceTest {

    @Autowired
    private RelationShipService shipService;

    @Test
    public void testFindAll(){
        long id = 24L;
        List<RelationShip> byHostId = shipService.findByHostId(id);
        for(RelationShip relationShip:byHostId){
            DateConverterConfig dateConverterConfig = new DateConverterConfig();
            String s = relationShip.getLastTime().toString();

            Date convert = dateConverterConfig.convert(s);
            System.out.println("查找的id是"+relationShip.getHostId()+"朋友的id是"+relationShip.getContactId()+"时间是"+convert);
        }
    }

    @Test
    public void testSave(){
        long id1 = 31L;
        long id2 = 40L;
        shipService.save(id1,id2);
    }
}