package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName Borrow
 * @Description 借阅类
 * @Author su
 * @Date 2023/5/7 22:25
 */
@Data
public class Borrow {
    int borrow_id;

    @JsonIgnore
    int book_id;

    String title;

    @JsonIgnore
    int account_id;

    String username;

    Timestamp borrow_time;

    Timestamp due_time;

    Timestamp actual_time;

    int del_flag;
}
