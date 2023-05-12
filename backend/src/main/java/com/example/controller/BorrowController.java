package com.example.controller;

import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.entity.RestBean;
import com.example.service.BorrowService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName BorrowController
 * @Description TODO
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

}
