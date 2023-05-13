package com.example.mapper;

import com.example.entity.Borrow;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName BorrowMapper
 * @Description TODO
 * @Author syp10
 * @Data 2023/5/7 22:02
 */
public interface BorrowMapper {

    @Results({
            @Result(column = "book_id", property = "title",
                    one = @One(select = "findTitleByBid")),
            @Result(column = "account_id", property = "username",
                    one = @One(select = "findUsernameById")),
    })
    @Select("select * from borrow")
    List<Borrow> getAllBorrow();

    @Results({
            @Result(column = "book_id", property = "title",
                    one = @One(select = "findTitleByBid")),
            @Result(column = "account_id", property = "username",
                    one = @One(select = "findUsernameById")),
    })
    @Select("select * from borrow where due_time < NOW() and del_flag = 0")
    List<Borrow> getOverdueBorrow();

    @Select("select title from book where bid = #{book_id}")
    String findTitleByBid(int book_id);

    @Select("select username from account where id = #{account_id}")
    String findUsernameById(int account_id);


    @Results({
            @Result(column = "book_id", property = "title",
                    one = @One(select = "findTitleByBid")),
            @Result(column = "account_id", property = "username",
                    one = @One(select = "findUsernameById")),
    })
    @Select("select * from borrow where del_flag = 0")
    List<Borrow> getUnreturnedBorrow();


    @Results({
            @Result(column = "book_id", property = "title",
                    one = @One(select = "findTitleByBid")),
            @Result(column = "account_id", property = "username",
                    one = @One(select = "findUsernameById")),
    })
    @Select("select * from borrow, account, book " +
            "where book_id = bid and account_id = id and username like '%${text}%' and borrow.del_flag = 0")
    List<Borrow> searchBorrowByUsername(String text);

    @Results({
            @Result(column = "book_id", property = "title",
                    one = @One(select = "findTitleByBid")),
            @Result(column = "account_id", property = "username",
                    one = @One(select = "findUsernameById")),
    })
    @Select("select * from borrow, account, book " +
            "where book_id = bid and account_id = id and title like '%${text}%' and borrow.del_flag = 0")
    List<Borrow> searchBorrowByTitle(String text);

    @Update("update borrow set due_time = DATE_ADD(due_time,INTERVAL 3 DAY) where borrow_id in (${ids})")
    int batchExtendBorrowByIds(String ids);

    @Update("update borrow set del_flag = 1, actual_time = NOW() where borrow_id in (${ids})")
    int batchReturnBorrowByIds(String ids);

    @Results({
            @Result(column = "book_id", property = "title",
                    one = @One(select = "findTitleByBid")),
            @Result(column = "account_id", property = "username",
                    one = @One(select = "findUsernameById")),
    })
    @Select("select * from borrow where account_id = #{account_id} and del_flag = 0")
    List<Borrow> selectBorrowingByAccountId(String account_id);
}
