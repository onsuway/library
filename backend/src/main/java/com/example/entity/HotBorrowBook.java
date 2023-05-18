package com.example.entity;

import lombok.Data;

/**
 * @ClassName BorrowCount
 * @Description 接收查询出的借阅次数多的书籍信息并传给前端
 * @Author su
 * @Date 2023/5/17 22:56
 */
@Data
public class HotBorrowBook {

    int book_id;

    String title;

    String cover_url;

    int borrow_count;
}
