package com.example.controller;

import com.example.entity.Account;
import com.example.entity.RestBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @ClassName UserController
 * @Description 用户信息相关的controller
 * @Author su
 * @Date 2023/4/23 21:37
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public RestBean<Account> me(@SessionAttribute("account") Account user){
        return RestBean.success(user);
    }



}
