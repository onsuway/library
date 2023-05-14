package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @ClassName Book
 * @Description 书籍类
 * @Author su
 * @Date 2023/5/2 16:49
 */
@Data
public class Book {
    int bid;

    String title;

    String author;

    String desc;

    @JsonIgnore
    int type_id;

    String type_name;

    int nums;
}
