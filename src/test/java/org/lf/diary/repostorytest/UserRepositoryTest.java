package org.lf.diary.repostorytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lf.diary.model.User;
import org.lf.diary.repository.ChatMessageRepository;
import org.lf.diary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: PengZH
 * @Date: 2020/3/7 21:32
 * @Description:
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {


    @Autowired
    private UserRepository repository;
    //用户名模糊匹配
    @Test
    public void test(){
        User u = new User();
        u.setUsername("Jvinh");
        List<User> byUsernameLike = repository.findByUsernameLike(u.getUsername());
        for(User user:byUsernameLike){
            System.out.println(user.getUsername());
        }
    }

}
