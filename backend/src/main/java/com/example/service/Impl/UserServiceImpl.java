package com.example.service.Impl;

import com.example.entity.Account;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import jakarta.annotation.Resource;
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

}
