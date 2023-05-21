package com.example.controller;

import com.example.entity.Borrow;
import com.example.entity.BorrowBookInfo;
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


    //admin-借阅管理页面 查询所有的正在借阅
    @PostMapping("/search")
    public RestBean<List<Borrow>> searchBorrowing(@RequestParam("search_type") String search_type,
                                                  @RequestParam("search_value")String search_value){
        List<Borrow> borrows = borrowService.searchBorrowingByTitleOrUsername(search_type, search_value);
        return RestBean.success(borrows);
    }

    //user-个人借阅页面 查询一个用户的历史借阅
    @PostMapping("/search-borrowed-with-account")
    public RestBean<List<BorrowBookInfo>> searchBorrowedWithAccount(@RequestParam("search_type") String search_type,
                                                                  @RequestParam("search_value")String search_value,
                                                                  @RequestParam("account_id") String account_id){
        List<BorrowBookInfo> searchBorrowed = borrowService.searchBorrowedByTitleOrAuthorWithAccount(search_type, search_value, account_id);
        return RestBean.success(searchBorrowed);
    }

    //  admin-批量续借 此接口为强制续借（不会判断是否续借过
    @PostMapping("/extend/{borrow_ids}")
    public RestBean<String> extendBorrow(@PathVariable String borrow_ids){
        int result = borrowService.extendBorrowByIds(borrow_ids);
        return RestBean.success("成功延长了" + result + "条借阅订单");
    }

    @PostMapping("/user-single-extend/{borrow_id}")
    public RestBean<String> userSingleExtend(@PathVariable String borrow_id){
        String message = borrowService.userSingleExtendById(borrow_id);

        if (message == null){
            return RestBean.success();
        }else {
            return RestBean.failure(401, message);
        }

    }

    //  admin-批量归还 此接口为强制归还（不会判断借阅是否逾期
    @PostMapping("/admin-batch-return/{borrow_ids}")
    public RestBean<String> adminBatchReturn(@PathVariable String borrow_ids){
        int result = borrowService.adminReturnBorrowByIds(borrow_ids);
        if (result == 0) {
            return RestBean.failure(401, "系统内部出现错误导致归还失败");
        }else {
            return RestBean.success("成功归还" + result + "本书");
        }
    }

    @PostMapping("/user-single-return")
    public RestBean<String> userSingleReturn(@RequestParam("borrow_id")String borrow_id,
                                             @RequestParam("account_id")String account_id){
        String message = borrowService.userSingleReturnById(borrow_id, account_id);

        if (message == null) {
            return RestBean.success();
        }else {
            return RestBean.failure(401, message);
        }
    }

    //获取某个用户的正在借阅
    @GetMapping("/get-user-borrowing/{account_id}")
    public RestBean<List<BorrowBookInfo>> getBorrowingByAccount(@PathVariable String account_id){
        return RestBean.success(borrowService.getBorrowingByAccountId(account_id));
    }

    //获取某个用户的历史借阅
    @GetMapping("/get-user-borrowed/{account_Id}")
    public RestBean<List<BorrowBookInfo>> getBorrowedByAccount(@PathVariable String account_Id){
        return RestBean.success(borrowService.getBorrowedByAccountId(account_Id));
    }

    //用户借阅接口
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



    //在所有范围内查询借阅数量最多的五本书
    @GetMapping("/get-hot-borrow-book")
    public RestBean<List<HotBorrowBook>> getHotBook(){
        return RestBean.success(borrowService.getHotBorrowBookTop5());
    }

    //在 类型范围/时间范围 内 查询借阅数量最多的十本书
    @PostMapping("/get-hot-borrow-leaderboard")
    public RestBean<List<HotBorrowBook>> getHotBorrowLeaderboard(@RequestParam("type_id") String type_id,
                                                                 @RequestParam("time_range")String time_range){
        return RestBean.success(borrowService.getHotBorrowBookTop10ByTimeAndType(type_id, time_range));
    }

}
