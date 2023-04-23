package com.example.service;

import com.example.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @ClassName AuthorizeService
 * @Description TODO
 * @Author syp10
 * @Data 2023/4/20 23:44
 */
public interface AuthorizeService extends UserDetailsService {
    String sendValidateEmail(String email, String sessionId, boolean hasAccount);

    String validateAndRegister(String username, String password, String email, String code, String sessionId);

    String validateOnly(String email, String code, String sessionId);

    boolean resetPassword(String password, String email);
}
