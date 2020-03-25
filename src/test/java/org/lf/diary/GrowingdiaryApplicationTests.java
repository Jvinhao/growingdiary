package org.lf.diary;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.lf.diary.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GrowingdiaryApplicationTests {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void test() {
        Jedis jedis = redisUtil.getJedis();

        jedis.close();
    }
}
