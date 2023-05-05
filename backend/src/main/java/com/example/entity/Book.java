package com.example.entity;

import lombok.Data;

/**
 * @ClassName Book
 * @Description TODO
 * @Author su
 * @Date 2023/5/2 16:49
 */
@Data
public class Book {
    int bid;
    String title;
    String author;
    String desc;

//    int type_id;
    String type_name;

    int nums;
}
