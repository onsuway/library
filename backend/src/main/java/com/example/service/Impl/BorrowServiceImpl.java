package com.example.service.Impl;

import com.example.entity.Borrow;
import com.example.entity.BorrowCount;
import com.example.mapper.BookMapper;
import com.example.mapper.BorrowMapper;
import com.example.mapper.UserMapper;
import com.example.service.BorrowService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName BorrowServiceImpl
 * @Description
 * @Author su
 * @Date 2023/5/7 22:05
 */
@Service
public class BorrowServiceImpl implements BorrowService {

    Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());

    //TODO 待将该变量提取到yaml文件里作为一个常量
    long borrowTimeLong = 7 * 24 * 60 * 60 * 1000L;

    @Resource
    BorrowMapper borrowMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    BookMapper bookMapper;

    Timestamp now = new Timestamp(System.currentTimeMillis());

    @Override
    public List<Borrow> getUnreturnedBorrow() {
        return borrowMapper.getUnreturnedBorrow();
    }

    @Override
    public List<Borrow> searchBorrowByTitleOrAuthor(String searchType, String searchValue) {
        if (Objects.equals(searchType, "title")) {
            return borrowMapper.searchBorrowByTitle(searchValue);
        } else if (Objects.equals(searchType, "username")) {
            return borrowMapper.searchBorrowByUsername(searchValue);
        } else return null;
    }

    @Override
    public List<Borrow> getOverdue() {
        return borrowMapper.getOverdueBorrow();
    }

    @Override
    public List<Borrow> getAllBorrow() {
        return borrowMapper.getAllBorrow();
    }

    @Override
    public List<Borrow> getWillOverdue() {
        return borrowMapper.getUnreturnedBorrow()
                .stream()
                .filter(borrow -> borrow.getDue_time().after(now)
                        && borrow.getDue_time().getTime() - now.getTime() < 24 * 60 * 60 * 1000)
                .toList();
    }

    @Override
    public int extendBorrowByIds(String ids) {
        return borrowMapper.batchExtendBorrowByIds(ids);
    }

    @Override
    public int returnBorrowByIds(String ids) {
        return borrowMapper.batchReturnBorrowByIds(ids);
    }

    @Override
    public List<Borrow> getBorrowingByAccountId(String account_id) {
        return borrowMapper.selectBorrowingByAccountId(account_id);
    }

    @Override
    @Transactional
    public String borrow(String bid, String account_id) {
        //获取  用户信用积分  和 正在借阅中的书籍数量
        int userCredit = userMapper.getUserCreditById(account_id);
        int userBorrowingNums = userMapper.getUserBorrowingNumsById(account_id);

        Timestamp due_time = new Timestamp(nowTimestamp.getTime() + borrowTimeLong);

        //判断用户积分是否合法
        if (userCredit == 0) {
            return "逾期借阅次数过多，请联系管理员缴纳罚款解封！";
        }

        //判断用户正在借阅中的书籍数量是否合法
        if (userBorrowingNums >= 3) {
            return "借阅的太多啦，先去读完再借叭";
        }

        //判断该用户是否借阅过该书
        if (borrowMapper.selectBorrowByBidAndAccountId(bid, account_id) > 0){
            return "已经借过这本书啦，快去读叭";
        }

        //书籍数量减一成功再进行插入借阅信息
        if (borrowMapper.decreaseBookNumsByBid(bid) == 1) {
            int result = borrowMapper.insertBorrow(bid, account_id, nowTimestamp, due_time);
            //成功借阅与否
            if (result == 1) {
                //用户正借阅书籍数量加1
                if(userMapper.increaseBorrowingNumsById(account_id) == 1){
                    return null;
                }else {
                    return "数据库内部出现错误，请联系管理员";
                }
            } else return "数据库内部出现错误，请联系管理员";

        } else {
            return "数据库内部出现错误，请联系管理员";
        }

    }

    @Override
    public List<BorrowCount> getHotBorrowedBookTop5() {

        List<BorrowCount> borrowCounts = borrowMapper.selectHotBorrowBook();

        borrowCounts.forEach(borrowCount -> {
            borrowCount.setTitle(bookMapper.selectTitleById(borrowCount.getBook_id()));
            borrowCount.setCover_url(bookMapper.selectCoverUrlByBookId(borrowCount.getBook_id()));
        });

        return borrowCounts;
    }

}
