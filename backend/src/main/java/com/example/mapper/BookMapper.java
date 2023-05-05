package com.example.mapper;

import com.example.entity.Book;
import com.example.entity.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName BookMapper
 * @Description TODO
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

    @Select("select type_name from type where type_id = #{type_id}")
    String getTypeNameByTypeId(int type_id);

    @Select("select type_id from type where type_name = #{type_name}")
    int getTypeIdByTypeName(String type_name);

    @Delete("delete from book where bid in (${ids})")
    int batchDeleteBookByIds(String ids);

    @Select("select * from type")
    List<Type> getAllType();

    @Update("update book set title = #{title}, author = #{author}, `desc` = #{desc}, type_id = #{type_id}, nums = #{nums} where bid = #{bid}")
    int editBookById(int bid, String title, String author, String desc, int type_id, int nums);

    @Insert("insert into book(title, author, `desc`, type_id, nums) values(#{title}, #{author}, #{desc}, #{type_id}, #{nums})")
    int addBook(String title, String author, String desc, int type_id, int nums);

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
}
