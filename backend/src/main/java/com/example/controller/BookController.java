package com.example.controller;

import com.example.entity.Book;
import com.example.entity.RestBean;
import com.example.entity.Type;
import com.example.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName BookController
 * @Description TODO
 * @Author su
 * @Date 2023/5/2 16:51
 */
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Resource
    BookService bookService;

    @GetMapping("/getAllBook")
    public RestBean<List<Book>> getAllBook() {
        return RestBean.success(bookService.getAllBook());
    }

    @GetMapping("/getAllType")
    public RestBean<List<Type>> getAllType(){
        return RestBean.success(bookService.getAllType());
    }

    @DeleteMapping("/delete-book/{ids}")
    public RestBean<String> batchDelete(@PathVariable String ids) {
        int result = bookService.batchDeleteBook(ids);
        return RestBean.success("成功删除了" + result + "本书");
    }

    @PostMapping("/edit")
    public RestBean<String> editBook(Book book){
        if (bookService.editBook(book) == 1){
            return RestBean.success("保存成功");
        }else {
            return RestBean.failure(400,"保存失败，请重启系统或联系管理员");
        }
    }

    @PostMapping("/addBook")
    public RestBean<String> addBook(Book book){
        if (bookService.addBook(book) == 1){
            return RestBean.success("新增成功");
        }else {
            return RestBean.failure(400, "新增失败，请重启系统或联系管理员");
        }
    }

    @PostMapping("/addType")
    public RestBean<String> addType(@RequestParam("type_name") String type_name){
        String message = bookService.addType(type_name);
        if (message == null){
            return RestBean.success("成功新增");
        }else {
            return RestBean.failure(400, message);
        }
    }

    @DeleteMapping("/delete-type/{type_id}")
    public RestBean<String> deleteType(@PathVariable int type_id){
        int result = bookService.deleteType(type_id);
        if (result == 1){
            return RestBean.success("成功删除");
        }else {
            return RestBean.failure(400, "删除失败，请重启系统或联系管理员");
        }
    }

    @PostMapping("/search")
    public RestBean<List<Book>> searchBook(@RequestParam("search_type") String search_type,
                                           @RequestParam("search_value")String search_value){
        List<Book> books = bookService.searchBookByTitleOrAuthor(search_type, search_value);
        return RestBean.success(books);
    }

    @GetMapping("/book-unique-count")
    public RestBean<Integer> getBookUniqueCount(){
        return RestBean.success(bookService.getBookUniqueCount());
    }

    @GetMapping("/book-total-count")
    public RestBean<Integer> getBookTotalCount(){
        return RestBean.success(bookService.getBookTotalCount());
    }

    @GetMapping("/type-count")
    public RestBean<Integer> getTypeCount(){
        return RestBean.success(bookService.getTypeCount());
    }

}
