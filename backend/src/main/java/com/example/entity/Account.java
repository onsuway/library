package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @ClassName Account
 * @Description 用户类
 * @Author su
 * @Date 2023/4/19 21:33
 */
@Data
public class Account {

    int id;

    String username;

    @JsonIgnore
    String password;

    String email;

    String role;

    int borrowing_nums;

    int credit;
}
