package com.example.service.Impl;

import com.example.entity.Borrow;
import com.example.entity.BorrowBookInfo;
import com.example.entity.HotBorrowBook;
import com.example.mapper.BorrowMapper;
import com.example.mapper.UserMapper;
import com.example.service.BorrowService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
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

    //TODO 待实现student/teacher分离后将该变量提取到yaml文件里作为一个常量
    long borrowTimeLong = 7 * 24 * 60 * 60 * 1000L;

    @Resource
    BorrowMapper borrowMapper;

    @Resource
    UserMapper userMapper;

    Timestamp now = new Timestamp(System.currentTimeMillis());

    @Override
    public List<Borrow> getUnreturnedBorrow() {
        return borrowMapper.getUnreturnedBorrow();
    }

    @Override
    public List<Borrow> searchBorrowingByTitleOrUsername(String searchType, String searchValue) {
        if (Objects.equals(searchType, "title")) {
            return borrowMapper.searchBorrowingByTitle(searchValue);
        } else if (Objects.equals(searchType, "username")) {
            return borrowMapper.searchBorrowingByUsername(searchValue);
        } else return null;
    }

    @Override
    public List<BorrowBookInfo> searchBorrowedByTitleOrAuthorWithAccount(String searchType, String searchValue, String account_id) {
        if (Objects.equals(searchType, "title")) {
            return borrowMapper.searchBorrowedByTitleWithAccount(searchValue, account_id);
        } else if (Objects.equals(searchType, "author")) {
            return borrowMapper.searchBorrowedByAuthorWithAccount(searchValue, account_id);
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
    @Transactional
    public int adminReturnBorrowByIds(String ids) {
        String[] borrowIdList = ids.split(",");
        List<String> accountIdList = Arrays
                .stream(borrowIdList)
                .map(borrowMapper::findAccountIdByBorrowId)
                .toList();

        // 拼接为一个字符串传给数据库一次删除 虽然不占用数据库
        // 但是当批量归还的借阅中有相同用户 会造成归还了多本书但只会减一次borrowing_nums
        // 所以只好采用for循环一次一次删除
        //String accountIds = String.join(",", accountIdList);
        accountIdList.forEach(account_id ->
                userMapper.decreaseBorrowingNumsById(account_id)
        );

        return borrowMapper.batchReturnBorrowByIds(ids);
    }

    @Override
    @Transactional
    public String userSingleReturnById(String borrow_id, String account_id) {
        Borrow borrow = borrowMapper.getBorrowById(borrow_id);

        //归还逾期
        if (borrow.getDue_time().before(now)){
            userMapper.decreaseCreditById(account_id);

            borrowMapper.userSingleReturn(borrow_id);

            return "归还逾期，扣除信用积分";
        }else {
            //方法自带扣除borrowing_nums
            borrowMapper.userSingleReturn(borrow_id);
            return null;
        }

    }

    @Override
    public String userSingleExtendById(String borrow_id) {

        if (borrowMapper.userSingleExtendBorrowing(borrow_id) == 1){
            return null;
        }else {
            return "已经续借过了，不能这么贪心哦";
        }
    }

    @Override
    public List<BorrowBookInfo> getBorrowingByAccountId(String account_id) {
        return borrowMapper.selectUserBorrowingBook(account_id);
    }

    @Override
    public List<BorrowBookInfo> getBorrowedByAccountId(String account_id) {
        return borrowMapper.selectUserBorrowedBook(account_id);
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
        if (borrowMapper.countBorrowingByBidAndAccountId(bid, account_id) > 0){
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
    public List<HotBorrowBook> getHotBorrowBookTop5() {
        return borrowMapper.selectHotBorrowBookTop5();
    }

    @Override
    public List<HotBorrowBook> getHotBorrowBookTop10ByTimeAndType(String type_id, String time_range) {

        //类型为全部 时间范围不是全部 只筛选时间范围
        if (Objects.equals(type_id, "all") && !Objects.equals(time_range, "all")){
            return borrowMapper.selectHotBorrowBookTop10WithTime(time_range);
        }

        //时间范围是全部 类型选择不是全部 只筛选类型
        if (!Objects.equals(type_id, "all") && Objects.equals(time_range, "all")){
            return borrowMapper.selectHotBorrowBookTop10WithType(type_id);
        }

        //两方都为all 不筛选
        if (Objects.equals(type_id, "all") && Objects.equals(time_range, "all")){
            return borrowMapper.selectHotBorrowBookTop10();
        }

        //都不为all 则进行双重筛选
        return borrowMapper.selectHotBorrowBookTop10WithTimeAndType(type_id, time_range);
    }

}
