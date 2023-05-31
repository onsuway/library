package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AuthorizeController
 * @Description 认证鉴权相关的controller
 * @Author su
 * @Date 2023/4/21 13:17
 */
@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    private final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$";
    private final String USERNAME_REGEX = "^[a-zA-Z0-9_一-龥]+$";
    private final String PASSWORD_REGEX = "^[^\\\\s]*$";


    @Resource
    AuthorizeService authorizeService;

    //给用户注册的邮箱地址 发验证码
    @PostMapping("/valid-register-email")
    public RestBean<String> validateRegisterEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                                  HttpSession session) {
        String s = authorizeService.sendValidateEmail(email, session.getId(), false);
        if (s == null) {
            return RestBean.success("邮件已发送，请注意查收");
        } else {
            return RestBean.failure(400, s);
        }
    }

    //给要 重置密码/忘记密码 的邮箱 发验证码
    @PostMapping("/valid-reset-email")
    public RestBean<String> validateResetEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                               HttpSession session) {
        String s = authorizeService.sendValidateEmail(email, session.getId(), true);
        if (s == null) {
            return RestBean.success("邮件已发送，请注意查收");
        } else {
            return RestBean.failure(400, s);
        }
    }

    //注册
    @PostMapping("/register")
    public RestBean<String> registerUser(@Pattern(regexp = USERNAME_REGEX) @Length(min = 2, max = 8) @RequestParam("username") String username,
                                         @Pattern(regexp = PASSWORD_REGEX) @Length(min = 6, max = 16) @RequestParam("password") String password,
                                         @Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                         @Length(min = 6, max = 6) @RequestParam("code") String code,
                                         @RequestParam("role") String role,
                                         HttpSession session) {
        String s = authorizeService.validateAndRegister(username, password, role, email, code, session.getId());
        if (s == null)
            return RestBean.success("注册成功");
        else
            return RestBean.failure(400, s);
    }


    //重置密码前验证用户邮箱的接口
    @PostMapping("/start-reset")
    public RestBean<String> startRest(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                      @Length(min = 6, max = 6) @RequestParam("code") String code,
                                      HttpSession session) {
        String s = authorizeService.validateOnly(email, code, session.getId());
        if (s == null) {
            session.setAttribute("reset-password", email);
            return RestBean.success();
        } else {
            return RestBean.failure(400, s);
        }
    }

    //根据是否验证通过来重置密码
    @PostMapping("/do-reset")
    public RestBean<String> resetPassword(@Pattern(regexp = PASSWORD_REGEX) @Length(min = 6, max = 16) @RequestParam("password") String password,
                                          HttpSession session) {
        String email = (String) session.getAttribute("reset-password");
        if (email == null) {
            return RestBean.failure(401, "请先通过邮箱验证码验证");
        } else if (authorizeService.resetPassword(password, email)) {
            session.removeAttribute("reset-password");
            return RestBean.success("密码重置成功");
        } else {
            return RestBean.failure(500, "内部错误，请联系管理员");
        }
    }

}













