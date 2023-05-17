package com.example;

import com.example.mapper.BookMapper;
import com.example.mapper.BorrowMapper;
import com.example.service.BorrowService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;

@SpringBootTest
class BackendApplicationTests {

    @Resource
    BookMapper bookMapper;

    @Resource
    BorrowMapper borrowMapper;

    @Resource
    BorrowService borrowService;


    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }

    @Resource
    StringRedisTemplate template;

    @Test
    void redis() {
        template.opsForValue().set("a","12");
    }


    @Test
    void deleted(){
        String ids = "2,3,4";
        System.out.println(bookMapper.batchDeleteBookByIds(ids));
    }



    @Test
    void add(){
        System.out.println(bookMapper.countTypeByTypeName("人文社科"));
    }

    @Test
    void search() {
        System.out.println(bookMapper.selectBookByAuthor("刘"));
    }

    @Test
    void borrow(){
        Timestamp borrow_time = new Timestamp(System.currentTimeMillis());
        Timestamp due_time = new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        System.out.println(borrowMapper.insertBorrow("66", "8", borrow_time, due_time));
    }

}
