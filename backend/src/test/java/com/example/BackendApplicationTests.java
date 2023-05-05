package com.example;

import com.example.mapper.BookMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class BackendApplicationTests {

    @Resource
    BookMapper bookMapper;

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
    void edit(){
        bookMapper.editBookById(11, "活着", "余华","《活着》讲述了在大时代背景下，随着内战、三反五反、大跃进、“文化大革命”" +
                "等社会变革，徐福贵的人生和家庭不断经受着苦难，到了最后所有亲人都先后离他而去，仅剩下年老的他和一头老牛相依为命。", 1, 61);
    }

    @Test
    void add(){
        System.out.println(bookMapper.countTypeByTypeName("人文社科"));
    }

    @Test
    void search() {
        System.out.println(bookMapper.selectBookByAuthor("刘"));
    }
}
