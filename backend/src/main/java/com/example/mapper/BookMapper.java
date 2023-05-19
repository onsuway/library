package com.example.mapper;

import com.example.entity.Book;
import com.example.entity.NewCreateBook;
import com.example.entity.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName BookMapper
 * @Description
 * @Author syp10
 * @Data 2023/5/2 16:50
 */
public interface BookMapper {

    @Select("select * from book")
    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    List<Book> getAllBook();

    @Select("select title from book where bid = #{book_id}")
    String selectTitleById(int book_id);

    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    @Select("select * from book where bid = #{book_id}")
    Book selectBookByBid(String book_id);

    @Select("select cover_url from book where bid = #{book_id}")
    String selectCoverUrlByBookId(int book_id);

    //严禁删除！！！ 用在别处
    @Select("select type_name from type where type_id = #{type_id}")
    String getTypeNameByTypeId(int type_id);

    @Select("select type_id from book where bid = #{book_id}")
    int getTypeIdByBookId(int book_id);

    @Select("select type_id from type where type_name = #{type_name}")
    int getTypeIdByTypeName(String type_name);

    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    @Select("select * from book where type_id = #{type_id} and bid <> #{book_id} limit 6")
    List<Book> getBookByTypeId(int type_id, int book_id);

    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    @Select("select * from book where author = #{author} and bid <> #{book_id} limit 5")
    List<Book> getBookByAuthor(String author, int book_id);

    @Select("select author from book where bid = #{book_id}")
    String getAuthorByBookId(int book_Id);

    @Delete("delete from book where bid in (${ids})")
    int batchDeleteBookByIds(String ids);

    @Select("select * from type")
    List<Type> getAllType();

    @Update("update book set title = #{title}, author = #{author}, `desc` = #{desc}, type_id = #{type_id}, nums = #{nums} , cover_url = #{cover_url} where bid = #{bid}")
    int editBookById(int bid, String title, String author, String desc, int type_id, int nums, String cover_url);

    @Insert("insert into book(title, author, `desc`, type_id, nums, cover_url) values(#{title}, #{author}, #{desc}, #{type_id}, #{nums}, #{cover_url})")
    int addBook(String title, String author, String desc, int type_id, int nums, String cover_url);

    @Insert("insert into type(type_name) values(#{type_name})")
    int addType(String type_name);

    @Select("select count(*) from type where type_name = #{type_name}")
    int countTypeByTypeName(String type_name);

    @Delete("delete from type where type_id = #{type_id}")
    int deleteTypeById(int type_id);

    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    @Select("select * from book where title like '%${text}%'")
    List<Book> selectBookByTitle(String text);

    @Results({
            @Result(column = "type_id", property = "type_name",
                    one = @One(select = "getTypeNameByTypeId")),
    })
    @Select("select * from book where author like '%${text}%'")
    List<Book> selectBookByAuthor(String text);

    @Select("select count(*) from book")
    int getBookUniqueCount();

    @Select("select sum(nums) from book")
    int getBookTotalCount();

    @Select("select count(*) from type")
    int getTypeCount();

    @Select("select bid as book_id, title, author, cover_url, create_day from book order by create_day desc limit 8")
    List<NewCreateBook> getNewCreateBook();
}

