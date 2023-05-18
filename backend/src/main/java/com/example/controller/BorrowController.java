package com.example.controller;

import com.example.entity.Borrow;
import com.example.entity.HotBorrowBook;
import com.example.entity.RestBean;
import com.example.service.BorrowService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName BorrowController
 * @Description 借阅接口
 * @Author su
 * @Date 2023/5/7 21:58
 */
@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Resource
    BorrowService borrowService;


    @GetMapping("/get-unreturned")
    public RestBean<List<Borrow>> getUnreturnedBorrow(){
        return RestBean.success(borrowService.getUnreturnedBorrow());
    }

    @GetMapping("/get-overdue")
    public RestBean<List<Borrow>> getOverdueBorrow(){
        return RestBean.success(borrowService.getOverdue());
    }

    @GetMapping("/get-all")
    public RestBean<List<Borrow>> getAllBorrow(){
        return RestBean.success(borrowService.getAllBorrow());
    }

    @GetMapping("/get-willOverdue")
    public RestBean<List<Borrow>> getWillOverdueBorrow(){
        return RestBean.success(borrowService.getWillOverdue());
    }

    @PostMapping("/search")
    public RestBean<List<Borrow>> searchBorrow(@RequestParam("search_type") String search_type,
                                           @RequestParam("search_value")String search_value){
        List<Borrow> borrows = borrowService.searchBorrowByTitleOrAuthor(search_type, search_value);
        return RestBean.success(borrows);
    }

    @PostMapping("/extend/{borrow_ids}")
    public RestBean<String> extendBorrow(@PathVariable String borrow_ids){
        int result = borrowService.extendBorrowByIds(borrow_ids);
        return RestBean.success("成功延长了" + result + "条借阅订单");
    }

    @PostMapping("/return/{borrow_ids}")
    public RestBean<String> returnBorrow(@PathVariable String borrow_ids){
        int result = borrowService.returnBorrowByIds(borrow_ids);
        return RestBean.success("成功归还了" + result + "条借阅订单");
    }

    @GetMapping("/get-borrowing-by-user/{account_id}")
    public RestBean<List<Borrow>> getBorrowingByAccount(@PathVariable String account_id){
        List<Borrow> borrowing = borrowService.getBorrowingByAccountId(account_id);
        return RestBean.success(borrowing);
    }

    @PostMapping("/user-borrow")
    public RestBean<String> userBorrow(@RequestParam("bid") String bid,
                                       @RequestParam("account_id") String account_id){
        String message = borrowService.borrow(bid, account_id);
        if (message == null){
            return RestBean.success();
        }else {
            return RestBean.failure(400, message);
        }
    }

    @GetMapping("/get-hot-borrow-book")
    public RestBean<List<HotBorrowBook>> getHotBook(){
        return RestBean.success(borrowService.getHotBorrowedBookTop5());
    }
}
