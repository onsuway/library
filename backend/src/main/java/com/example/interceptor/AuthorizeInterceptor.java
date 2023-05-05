package com.example.interceptor;

import com.example.entity.user.AccountUser;
import com.example.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @ClassName AuthorizeInterceptor
 * @Description 登录鉴权相关的过滤器
 * @Author su
 * @Date 2023/4/23 21:42
 */
@Component
public class AuthorizeInterceptor implements HandlerInterceptor {
    @Resource
    UserMapper userMapper;

    /**
     * @description 从context中获取用户信息并添加到session的attribute中
     * @author su
     * @return boolean
     * @date 2023/4/25 22:39
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        AccountUser accountUser = new AccountUser(userMapper.findAccountByUsernameOrEmail(username));
        request.getSession().setAttribute("account", accountUser);
        return true;
    }
}
