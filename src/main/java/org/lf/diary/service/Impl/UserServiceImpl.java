package org.lf.diary.service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.lf.diary.core.AccessTokenDTO;
import org.lf.diary.core.MailVo;
import org.lf.diary.core.ServiceException;
import org.lf.diary.core.SexJudge;
import org.lf.diary.model.Authorize;
import org.lf.diary.model.QqModel;
import org.lf.diary.model.User;
import org.lf.diary.model.Vo.SafeUserVo;
import org.lf.diary.provider.QqProvider;
import org.lf.diary.repository.AuthorizeRepository;
import org.lf.diary.repository.MailVoRepository;
import org.lf.diary.repository.UserRepository;
import org.lf.diary.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/21 22:37
 * @Description: TODO
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private MailVoRepository mailVoRepository;

    @Autowired
    private AuthorizeRepository authorizeRepository;

    @Autowired
    private QqProvider qqProvider;

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void updateUserImg(String userImg, Long id) {
        if (!StringUtils.equals(userImg, "")) {
            userRepository.updateUserImg(userImg, id);
        }

    }

    @Override
    public User saveUser(User user) {
        User userDb = userRepository.findById(user.getId()).orElseThrow(() -> new ServiceException("id不存在"));
        user.setUserImg(userDb.getUserImg());
        user.setPassword(userDb.getPassword());
        user.setEmail(userDb.getEmail());
        user.setToken(userDb.getToken());
        user.setCreateTime(userDb.getCreateTime());
        user.setModifyTime(new Date());
        return userRepository.save(user);
    }

    @Override
    public Long countUser() {
        return userRepository.count();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean findPassword(User user, String validateCode) {
        MailVo mailVo = mailVoRepository.findByValidateCode(validateCode);
        if (mailVo != null) {
            String passwordCode = encoder.encode(user.getPassword());
            userRepository.updatePassword(passwordCode, user.getEmail());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public User findUser(AccessTokenDTO accessTokenDTO) {
        Optional<Authorize> optional = authorizeRepository.findByOpenId(accessTokenDTO.getOpenId());
        if (optional.isPresent()) {
            Authorize authorize = optional.get();
            return authorize.getUser();
        } else {
            //查不到就做保存操作


            return null;
        }

    }

    @Override
    public User bindUser(User user, String openId) {
        List<User> users = userRepository.findAllByEmail(user.getEmail());
        for (User u : users) {
            boolean b = encoder.matches(user.getPassword(), u.getPassword());
            if (b) {
                Authorize authorize = new Authorize();
                authorize.setUserId(u.getId());
                authorize.setOpenId(openId);
                authorizeRepository.save(authorize);
                return u;
            }
        }
        return null;
    }

    @Override
    public User quickRegister(AccessTokenDTO accessTokenDTO) {
        QqModel qqInfo = qqProvider.getUserInfo(accessTokenDTO);
        User user = new User();
        user.setUsername(qqInfo.getNickname());
        user.setPassword(encoder.encode("12345678"));
        user.setUserImg(qqInfo.getFigureurl_qq_1());
        user.setSex(SexJudge.judgeGender(qqInfo.getGender()));
        user.setToken(UUID.randomUUID().toString());
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        User userDb = userRepository.save(user);
        Authorize authorize = new Authorize();
        authorize.setOpenId(accessTokenDTO.getOpenId());
        authorize.setUserId(userDb.getId());
        authorizeRepository.save(authorize);
        return userDb;
    }

    //返回用户的安全信息
    @Override
    public User findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ServiceException("id不存在"));
        User u = new User();
        u.setId(user.getId());
        u.setUsername(user.getUsername());
        u.setUserImg(user.getUserImg());
        return u;
    }

    //按前台输入的关键字查找所有的user用户
    @Override
    public List<SafeUserVo> searchFriend(String keyword) {
        List<User> userList = userRepository.findByUsernameLikeTop5(keyword);
        //转化为安全的user信息
        List<SafeUserVo> safeUserVos = new ArrayList<>();
        for(User u:userList){
            SafeUserVo safeUserVo = new SafeUserVo();
            BeanUtils.copyProperties(u,safeUserVo);
            safeUserVos.add(safeUserVo);
        }

        return  safeUserVos;
    }
}
