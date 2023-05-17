package com.example.service.Impl;

import com.example.entity.Account;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description
 * @Author su
 * @Date 2023/5/13 8:37
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public List<Account> getAllAccountUser() {
        return userMapper.getAllAccountUser();
    }

    @Override
    public int resetCredit(String account_id) {
        if (userMapper.resetCreditById(account_id) == 1){
            return 1;
        }else return 0;
    }

    @Override
    public int getUserCount() {
        return userMapper.getUserCount();
    }

    @Override
    public List<Account> searchAccountByUsername(String text) {
        return userMapper.searchAccountByUsername(text);
    }

    @Override
    public String resetPassword(String account_id, String current_password, String new_password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //得到当前加密过的密码
        String current_password_encrypted = userMapper.getPasswordByAccountId(account_id);

        //加密过的密码与用户输入的明文密码对比
        if (passwordEncoder.matches(current_password, current_password_encrypted)){
            String new_password_encrypted = passwordEncoder.encode(new_password);

            if (userMapper.setNewPasswordByAccountId(account_id, new_password_encrypted) == 1){
                return null;
            }else {
                return "数据库内部出现错误，请联系管理员";
            }

        }else {
            return "输入的原密码错误";
        }

    }
}
