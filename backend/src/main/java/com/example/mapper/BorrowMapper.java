package com.example.mapper;

import com.example.entity.Borrow;
import com.example.entity.BorrowBookInfo;
import com.example.entity.HotBorrowBook;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName BorrowMapper
 * @Description
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

    //严禁删除！
    @Select("select title from book where bid = #{book_id}")
    String findTitleByBid(int book_id);
    //严禁删除！
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

    @Update("update book set nums = nums - 1 where bid = #{bid} and nums > 0")
    int decreaseBookNumsByBid(String bid);

    @Insert("insert into borrow (book_id, account_id, borrow_time, due_time) values (#{book_id}, #{account_id}, #{borrow_time}, #{due_time})")
    int insertBorrow(String book_id, String account_id, Timestamp borrow_time, Timestamp due_time);

    @Select("select count(*) from borrow where book_id = #{book_id} and account_id = #{account_id}")
    int selectBorrowByBidAndAccountId(String book_id, String account_id);

    @Select("select book_id, count(*) as borrow_count from borrow group by book_id order by borrow_count DESC LIMIT 5")
    List<HotBorrowBook> selectHotBorrowBook();

    @Select("""
            select borrow_id, borrow.book_id, book.title ,book.author, borrow.borrow_time, borrow.due_time, borrow.actual_time\s
            from book, borrow\s
            where book.bid = borrow.book_id and borrow.account_id = #{account_id} and borrow.del_flag = 0""")
    List<BorrowBookInfo> selectUserBorrowingBook(String account_id);

}
