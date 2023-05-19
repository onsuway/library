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

    List<Borrow> searchBorrowByTitleOrAuthor(String searchType, String searchValue);

    List<Borrow> getOverdue();

    List<Borrow> getAllBorrow();

    List<Borrow> getWillOverdue();

    int extendBorrowByIds(String ids);

    int returnBorrowByIds(String ids);

    List<BorrowBookInfo> getBorrowingByAccountId(String account_id);

    String borrow(String bid, String accountId);

    List<HotBorrowBook> getHotBorrowedBookTop5();

}
