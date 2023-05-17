package com.example;

import com.example.entity.BorrowCount;
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


    @Test
    void contextLoads() {

        List<BorrowCount> borrowCounts = borrowMapper.selectHotBorrowBook();

        System.out.println(borrowCounts);


    }


}
