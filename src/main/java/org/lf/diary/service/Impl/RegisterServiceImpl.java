package org.lf.diary.service.Impl;

import org.lf.diary.core.Constant;
import org.lf.diary.model.User;
import org.lf.diary.repository.UserRepository;
import org.lf.diary.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/20 21:41
 * @Description: TODO
 */

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Constant constant;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Override
    public User doRegister(User user) {
        User user1 = userRepository.findByUsername(user.getUsername());
        if(user1 != null) {
            return null;
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setSex(1);
        user.setToken(UUID.randomUUID().toString());
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        user.setUserImg(constant.getDefaultImg());
        User userDb = userRepository.save(user);
        return userDb;
    }

    @Override
    public Boolean checkUserName(String username) {
        List<User> users = userRepository.countByUsername(username);
        if (users.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
