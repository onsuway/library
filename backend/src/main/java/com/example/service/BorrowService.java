package com.example.service;

import com.example.entity.Borrow;
import com.example.entity.BorrowBookInfo;
import com.example.entity.HotBorrowBook;

import java.util.List;

/**
 * @ClassName BorrowService
 * @Description
 * @Author syp10
 * @Data 2023/5/7 22:04
 */
public interface BorrowService {

    List<Borrow> getUnreturnedBorrow();

    List<Borrow> searchBorrowingByTitleOrUsername(String searchType, String searchValue);

    List<BorrowBookInfo> searchBorrowedByTitleOrAuthorWithAccount(String searchType, String searchValue, String account_id);

    List<Borrow> getOverdue();

    List<Borrow> getAllBorrow();

    List<Borrow> getWillOverdue();

    int extendBorrowByIds(String ids);

    int adminReturnBorrowByIds(String ids);

    List<BorrowBookInfo> getBorrowingByAccountId(String account_id);

    List<BorrowBookInfo> getBorrowedByAccountId(String account_id);

    String borrow(String bid, String accountId);

    List<HotBorrowBook> getHotBorrowBookTop5();

    String userSingleReturnById(String borrow_id, String account_id);

    String userSingleExtendById(String borrow_id);

    List<HotBorrowBook> getHotBorrowBookTop10ByTimeAndType(String type_id, String time_range);
}
