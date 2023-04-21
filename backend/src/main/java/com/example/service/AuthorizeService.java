package com.example.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @ClassName AuthorizeService
 * @Description TODO
 * @Author syp10
 * @Data 2023/4/20 23:44
 */
public interface AuthorizeService extends UserDetailsService {
    boolean sendValidateEmail(String email, String sessionId);
}
