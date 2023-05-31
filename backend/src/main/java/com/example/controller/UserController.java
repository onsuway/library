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

    //从session中获取个人信息
    @GetMapping("/me")
    public RestBean<Account> me(@SessionAttribute("account") Account user) {
        return RestBean.success(user);
    }

    @GetMapping("/get-all")
    public RestBean<List<Account>> getAllAccountUser() {
        return RestBean.success(userService.getAllAccountUser());
    }

    @PostMapping("/reset-credit")
    public RestBean<String> resetCredit(@RequestParam("id") String id) {
        if (userService.resetCredit(id) == 1) {
            return RestBean.success("成功重置");
        } else {
            return RestBean.failure(400, "重置时发生了错误，请联系管理员");
        }
    }

    @PostMapping("/reset-password")
    public RestBean<String> resetPassword(@RequestParam("account_id") String account_id,
                                          @RequestParam("current_password") String current_password,
                                          @RequestParam("new_password") String new_password) {
        String message = userService.resetPassword(account_id, current_password, new_password);

        if (message == null) {
            return RestBean.success("重置密码成功");
        } else {
            return RestBean.failure(401, message);
        }
    }

    //admin主页展示用户数量
    @GetMapping("/user-count")
    public RestBean<Integer> getUserCount() {
        return RestBean.success(userService.getUserCount());
    }

    @PostMapping("/search")
    public RestBean<List<Account>> searchAccountByUsername(@RequestParam("text") String text) {
        return RestBean.success(userService.searchAccountByUsername(text));
    }

    @PostMapping("/change-role")
    public RestBean<String> changeRole(@RequestParam("account_id") String account_id,
                                       @RequestParam("new_role") String new_role) {
        String message = userService.changeUserRole(account_id, new_role);
        if (message == null){
            return RestBean.success("成功更改用户角色");
        }else {
            return RestBean.failure(401, message);
        }

    }

}
