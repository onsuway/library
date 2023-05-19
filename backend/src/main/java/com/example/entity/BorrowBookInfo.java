package com.example.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName BorrowBookInfo
 * @Description 该类用于给前端传递用户借阅书籍的相关信息
 * @Author su
 * @Date 2023/5/19 22:50
 */
@Data
public class BorrowBookInfo {

    int borrow_id;

    int book_id;

    String title;

    String author;

    Timestamp borrow_time;

    Timestamp due_time;

    Timestamp actual_time;
}
