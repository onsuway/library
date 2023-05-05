package com.example.entity.user;

import com.example.entity.auth.Account;
import lombok.Data;

/**
 * @ClassName User
 * @Description TODO
 * @Author su
 * @Date 2023/4/23 21:38
 */
@Data
public class AccountUser {
    int id;
    String username;
    String email;
    String role;

    public AccountUser(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.role = account.getRole();
    }
}
