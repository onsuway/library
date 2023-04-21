package com.example.entity;

import lombok.Data;

/**
 * @ClassName User
 * @Description TODO
 * @Author su
 * @Date 2023/4/19 21:33
 */
@Data
public class Account {

    int id;

    String username;

    String password;

    String email;
}
