package com.example.mapper;

import com.example.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author su
 * @Date 2023/4/19 21:32
 */
public interface UserMapper {

    @Select("select * from db_account where username = #{text} or email = #{text}")
    Account findAccountByUsernameOrEmail(String text);

    @Insert("insert into db_account (username, password, email) values (#{username}, #{password}, #{email})")
    int CreatAccount(String username, String password, String email);
}
