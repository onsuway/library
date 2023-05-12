package com.example.service.Impl;

import com.example.entity.Account;
import com.example.mapper.UserMapper;
import com.example.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AuthorizeService
 * @Description 用户登录相关的service实现类
 * @Author su
 * @Date 2023/4/19 21:31
 */
@Service
public class AuthorizeServiceImpl implements AuthorizeService {
    @Resource
    UserMapper userMapper;

    @Value("${spring.mail.username}")
    String from;

    @Resource
    MailSender mailSender;

    @Resource
    StringRedisTemplate template;


    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) throw new UsernameNotFoundException("用户名不能为空");

        Account account = userMapper.findAccountByUsernameOrEmail(username);

        if (account == null) throw new UsernameNotFoundException("用户名或密码错误");

        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }


    @Override
    public String sendValidateEmail(String email, String sessionId, boolean hasAccount) {
        String key = "email:" + sessionId + ":" + email + ":" + hasAccount;

        if (Boolean.TRUE.equals(template.hasKey(key))) {
            long expire = Optional.ofNullable(template.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120) return "请求频繁，请稍后再试";
        }

        Account account = userMapper.findAccountByUsernameOrEmail(email);

        if (hasAccount && account == null) return "没有匹配该邮箱的用户";
        if (!hasAccount && account != null) return "此邮箱已被注册";

        Random random = new Random();
        int code = random.nextInt(899999) + 100000; //保持6位数
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(email);
        mailMessage.setSubject("您的验证邮件");
        mailMessage.setText("验证码是：" + code);
        try {
            mailSender.send(mailMessage);

            template.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            return null;
        } catch (MailException e) {
            e.printStackTrace();
            return "邮件发送失败，请检查邮件地址是否有效";
        }

    }

    @Override
    public String validateAndRegister(String username, String password, String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email + ":false";
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            String s = template.opsForValue().get(key);
            if (s == null) return "验证码失效，请重新请求";
            if (s.equals(code)) {
                Account account = userMapper.findAccountByUsernameOrEmail(username);
                if (account != null) return "此用户名已被注册";
                template.delete(key);
                password = encoder.encode(password);
                if (userMapper.creatAccount(username, password, email) > 0) {
                    return null;
                } else {
                    return "内部错误，请联系管理员";
                }
            } else {
                return "验证码错误";
            }
        } else {
            return "请先通过邮箱验证码验证";
        }
    }

    @Override
    public String validateOnly(String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email + ":true";
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            String s = template.opsForValue().get(key);
            if (s == null) return "验证码失效，请重新请求";
            if (s.equals(code)) {
                template.delete(key);
                return null;
            } else {
                return "验证码错误";
            }
        } else {
            return "请先通过邮箱验证码验证";
        }
    }

    @Override
    public boolean resetPassword(String password, String email) {
        password = encoder.encode(password);
        return userMapper.resetPasswordByEmail(password,email) > 0;
    }
}
