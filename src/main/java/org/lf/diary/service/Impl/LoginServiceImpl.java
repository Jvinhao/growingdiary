package org.lf.diary.service.Impl;

import com.alibaba.fastjson.JSON;
import org.lf.diary.model.User;
import org.lf.diary.repository.UserRepository;
import org.lf.diary.service.LoginService;
import org.lf.diary.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/14 14:52
 * @Description: TODO
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public User doLogin(User user) {
        User userDb = userRepository.findByUsername(user.getUsername());
        if (userDb == null) {
            List<User> users = userRepository.findAllByEmail(user.getUsername());
            if (users.size() != 0) {
                User user1 = null;
                for (User u : users) {
                    user1 = checkUser(user, u);
                    if (user1 != null) {
                        break;
                    }
                }
                return user1;
            } else {
                return null;
            }

        } else {
            return checkUser(user, userDb);
        }
    }

    private User checkUser(User user, User userDb) {
        Jedis jedis = redisUtil.getJedis();
        boolean b = encoder.matches(user.getPassword(), userDb.getPassword());
        if (b) {
            String userToRedis = JSON.toJSONString(userDb);
            jedis.set(userDb.getToken(), userToRedis);
            jedis.expire(userDb.getToken(), 60 * 60 * 24);
            redisUtil.close(jedis);
            return userDb;
        } else {
            redisUtil.close(jedis);
            return null;
        }
    }
}
