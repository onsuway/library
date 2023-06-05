package com.example.mapper;

import com.example.entity.Book;
import com.example.entity.NewCreateBook;
import com.example.entity.Type;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @ClassName BookMapper
 * @Description
 * @Author syp10
 * @Data 2023/5/2 16:50
 */
public interface BookMapper {

    @Select("select * from book where del_flag = 0")
    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    List<Book> getAllBook();

    @Select("select title from book where bid = #{book_id} and del_flag = 0")
    String selectTitleById(int book_id);

    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    @Select("select * from book where bid = #{book_id} and del_flag = 0")
    Book selectBookByBid(String book_id);

    @Select("select cover_url from book where bid = #{book_id} and del_flag = 0")
    String selectCoverUrlByBookId(int book_id);

    //严禁删除！！！ 用在别处
    @Select("select type_name from type where type_id = #{type_id} and del_flag = 0")
    String getTypeNameByTypeId(int type_id);

    @Select("select type_id from book where bid = #{book_id}")
    int getTypeIdByBookId(int book_id);

    @Select("select type_id from type where type_name = #{type_name} and del_flag = 0")
    int getTypeIdByTypeName(String type_name);

    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    @Select("select * from book where type_id = #{type_id} and bid <> #{book_id} and del_flag = 0 limit 6")
    List<Book> getBookByTypeId(int type_id, int book_id);

    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    @Select("select * from book where author = #{author} and bid <> #{book_id} and del_flag = 0 limit 5 ")
    List<Book> getBookByAuthor(String author, int book_id);

    @Select("select author from book where bid = #{book_id} and del_flag = 0")
    String getAuthorByBookId(int book_Id);

    @Update("""
            UPDATE book
            SET del_flag = 1
            WHERE bid IN (${ids})
            AND del_flag = 0
            AND NOT EXISTS (
            SELECT 1 FROM borrow WHERE book_id = book.bid AND del_flag = 0
            )""")
    int batchDeleteBookByIds(String ids);

    @Select("select * from type where del_flag = 0")
    List<Type> getAllType();

    @Update("update book set title = #{title}, author = #{author}, `desc` = #{desc}, type_id = #{type_id}, nums = #{nums} , cover_url = #{cover_url} where bid = #{bid} and del_flag = 0")
    int editBookById(int bid, String title, String author, String desc, int type_id, int nums, String cover_url);

    @Insert("insert into book(title, author, `desc`, type_id, nums, cover_url, create_day) values(#{title}, #{author}, #{desc}, #{type_id}, #{nums}, #{cover_url}, #{create_day})")
    int addBook(String title, String author, String desc, int type_id, int nums, String cover_url, Date create_day);

    @Insert("insert into type(type_name) values(#{type_name})")
    int addType(String type_name);

    @Select("select count(*) from type where type_name = #{type_name} and del_flag = 0")
    int countTypeByTypeName(String type_name);

    @Update("update type set del_flag = 1 where type_id = #{type_id} and del_flag = 0")
    int deleteTypeById(int type_id);

    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    @Select("select * from book where title like '%${text}%' and del_flag = 0")
    List<Book> selectBookByTitle(String text);

    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    @Select("select * from book where author like '%${text}%' and del_flag = 0")
    List<Book> selectBookByAuthor(String text);

    @Select("select count(*) from book where del_flag = 0")
    int getBookUniqueCount();

    @Select("select sum(nums) from book where del_flag = 0 ")
    int getBookTotalCount();

    @Select("select count(*) from type where del_flag = 0")
    int getTypeCount();

    @Select("select bid as book_id, title, author, cover_url, create_day from book where del_flag = 0 order by create_day desc limit 8")
    List<NewCreateBook> getNewCreateBook();
}

