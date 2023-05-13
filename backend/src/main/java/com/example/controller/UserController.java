package com.example.controller;

import com.example.entity.Account;
import com.example.entity.RestBean;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UserController
 * @Description 用户信息相关的controller
 * @Author su
 * @Date 2023/4/23 21:37
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/me")
    public RestBean<Account> me(@SessionAttribute("account") Account user){
        return RestBean.success(user);
    }

    @GetMapping("/get-all")
    public RestBean<List<Account>> getAllAccountUser() {
        return RestBean.success(userService.getAllAccountUser());
    }

    @PostMapping("/reset-credit")
    public RestBean<String> resetCredit(@RequestParam("id") String id){
        if (userService.resetCredit(id) == 1){
            return RestBean.success("成功重置");
        }else {
            return RestBean.failure(400, "重置时发生了错误，请联系管理员");
        }
    }

    @GetMapping("/user-count")
    public RestBean<Integer> getUserCount(){
        return RestBean.success(userService.getUserCount());
    }

    @PostMapping("/search")
    public RestBean<List<Account>> searchAccountByUsername(@RequestParam("text") String text){
        return RestBean.success(userService.searchAccountByUsername(text));
    }
}
