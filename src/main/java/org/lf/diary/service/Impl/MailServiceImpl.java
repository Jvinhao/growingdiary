package org.lf.diary.service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.lf.diary.core.MailVo;
import org.lf.diary.repository.MailVoRepository;
import org.lf.diary.service.MailService;
import org.lf.diary.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;

/**
 * @Author: Jvinh
 * @DateTime: 2020/2/7 19:41
 * @Description: TODO
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value(value = "${spring.mail.username}")
    private String fromUser;

    @Autowired
    private MailVoRepository mailVoRepository;

    @Override
    public String sendValidateCode(String email) {
        MailVo mailVo = new MailVo();
        mailVo.setMailSub("验证码");
        String code = ValidateCodeUtils.createCode();
        String text = "您好,您的验证码为: " + "<span style='width:55px;color:red;background:yellow'>" + code + "</span>,请在五分钟之内输入哦！";

        mailVo.setValidateCode(code);
        mailVo.setToUser(email);
        mailVo.setFromUser(fromUser);
        mailVo.setSentDate(new Date());
        mailVo.setTimeOut(new Date(System.currentTimeMillis() + 5 * 60 * 1000));

        //1.检测邮件
        checkMail(mailVo);
        //2.发送邮件
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            messageHelper.setFrom(mailVo.getFromUser());
            messageHelper.setTo(mailVo.getToUser());
            messageHelper.setSubject(mailVo.getMailSub());
            messageHelper.setText(text, true);
            mailSender.send(messageHelper.getMimeMessage());
            //保存到数据库
            mailVoRepository.save(mailVo);
            return code;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "500";
    }

    private void checkMail(MailVo mailVo) {
        if (StringUtils.isEmpty(mailVo.getToUser())) {
            throw new RuntimeException("邮件收信人不能为空");
        }
        if (StringUtils.isEmpty(mailVo.getMailSub())) {
            throw new RuntimeException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(mailVo.getValidateCode())) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }
}
