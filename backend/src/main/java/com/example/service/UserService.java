package com.example.service;

import com.example.entity.Account;

import java.util.List;

/**
 * @ClassName UserService
 * @Description
 * @Author syp10
 * @Data 2023/5/13 8:37
 */
public interface UserService {

    List<Account> getAllAccountUser();

    int resetCredit(String account_id);

    int getUserCount();

    List<Account> searchAccountByUsername(String text);
}
