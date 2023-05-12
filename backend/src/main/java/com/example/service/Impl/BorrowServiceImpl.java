package com.example.service.Impl;

import com.example.entity.Borrow;
import com.example.mapper.BorrowMapper;
import com.example.service.BorrowService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName BorrowServiceImpl
 * @Description TODO
 * @Author su
 * @Date 2023/5/7 22:05
 */
@Service
public class BorrowServiceImpl implements BorrowService {

    @Resource
    BorrowMapper borrowMapper;

    Timestamp now = new Timestamp(System.currentTimeMillis());

    @Override
    public List<Borrow> getUnreturnedBorrow() {
        return borrowMapper.getUnreturnedBorrow();
    }

    @Override
    public List<Borrow> searchBorrowByTitleOrAuthor(String searchType, String searchValue) {
        if (Objects.equals(searchType, "title")){
            return borrowMapper.searchBorrowByTitle(searchValue);
        } else if (Objects.equals(searchType, "username")) {
            return borrowMapper.searchBorrowByUsername(searchValue);
        }else return null;
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
                        && borrow.getDue_time().getTime() - now.getTime() <24 * 60 * 60 * 1000)
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

}
