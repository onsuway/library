package com.example.mapper;

import com.example.entity.auth.Account;
import com.example.entity.user.AccountUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @ClassName UserMapper
 * @Description user的mapper类
 * @Author su
 * @Date 2023/4/19 21:32
 */
public interface UserMapper {

    @Select("select * from account where username = #{text} or email = #{text}")
    Account findAccountByUsernameOrEmail(String text);


    @Insert("insert into account (username, password, email) values (#{username}, #{password}, #{email})")
    int creatAccount(String username, String password, String email);

    @Update("update account set password = #{password} where email = #{email}")
    int resetPasswordByEmail(String password, String email);
}
