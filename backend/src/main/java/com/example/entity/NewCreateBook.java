package com.example.entity;

import lombok.Data;

/**
 * @ClassName NewCreateBook
 * @Description 接收查询出的最新上架的书籍信息并传给前端
 * @Author su
 * @Date 2023/5/18 15:32
 */
@Data
public class NewCreateBook {

    int book_id;

    String title;

    String author;

    String cover_url;

    String create_day;
}
