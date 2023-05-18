package com.example;

import com.example.entity.HotBorrowBook;
import com.example.mapper.BookMapper;
import com.example.mapper.BorrowMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {

    @Resource
    BorrowMapper borrowMapper;

    @Resource
    BookMapper bookMapper;


    @Test
    void contextLoads() {

        List<HotBorrowBook> hotBorrowBooks = borrowMapper.selectHotBorrowBook();

        System.out.println(hotBorrowBooks);
    }

    @Test
    void Time(){
        System.out.println(bookMapper.getNewCreateBook());
    }


}
