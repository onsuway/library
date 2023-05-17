package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @ClassName BorrowCount
 * @Description 该类用来查询借阅次数多的书籍信息
 * @Author su
 * @Date 2023/5/17 22:56
 */
@Data
public class BorrowCount {

    @JsonIgnore
    int book_id;

    String title;

    String cover_url;

    int borrow_count;

}
