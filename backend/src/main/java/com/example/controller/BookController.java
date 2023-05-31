package com.example.controller;

import com.example.entity.Book;
import com.example.entity.NewCreateBook;
import com.example.entity.RestBean;
import com.example.entity.Type;
import com.example.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName BookController
 * @Description
 * @Author su
 * @Date 2023/5/2 16:51
 */
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Resource
    BookService bookService;

    @PostMapping("/get-by-id")
    public RestBean<Book> getBookById(@RequestParam("book_id") String book_id){
        return RestBean.success(bookService.getBookById(book_id));
    }

    @GetMapping("/get-all")
    public RestBean<List<Book>> getAllBook() {
        return RestBean.success(bookService.getAllBook());
    }

    @GetMapping("/get-all-type")
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

    @PostMapping("/add")
    public RestBean<String> addBook(Book book){
        if (bookService.addBook(book) == 1){
            return RestBean.success("新增成功");
        }else {
            return RestBean.failure(400, "新增失败，请重启系统或联系管理员");
        }
    }

    @PostMapping("/add-type")
    public RestBean<String> addType(@RequestParam("type_name") String type_name){
        String message = bookService.addType(type_name);
        if (message == null){
            return RestBean.success("成功新增");
        }else {
            return RestBean.failure(400, message);
        }
    }

    //删除
    @DeleteMapping("/delete-type/{type_id}")
    public RestBean<String> deleteType(@PathVariable int type_id){
        int result = bookService.deleteType(type_id);
        if (result == 1){
            return RestBean.success("成功删除");
        }else {
            return RestBean.failure(400, "删除失败，请重启系统或联系管理员");
        }
    }

    //搜索（按书名/作者
    @PostMapping("/search")
    public RestBean<List<Book>> searchBook(@RequestParam("search_type") String search_type,
                                           @RequestParam("search_value")String search_value){
        List<Book> books = bookService.searchBookByTitleOrAuthor(search_type, search_value);
        return RestBean.success(books);
    }

    //获取书籍种数
    @GetMapping("/book-unique-count")
    public RestBean<Integer> getBookUniqueCount(){
        return RestBean.success(bookService.getBookUniqueCount());
    }

    //获取书籍总本量
    @GetMapping("/book-total-count")
    public RestBean<Integer> getBookTotalCount(){
        return RestBean.success(bookService.getBookTotalCount());
    }

    //获取类型数量
    @GetMapping("/type-count")
    public RestBean<Integer> getTypeCount(){
        return RestBean.success(bookService.getTypeCount());
    }

    //获取最新上架的8本书籍
    @GetMapping("/get-new-create")
    public RestBean<List<NewCreateBook>> getNewCreateBook(){
        return RestBean.success(bookService.getNewCreateBook());
    }

    //根据书籍ID 获取同类型的书籍
    @PostMapping("/get-same-type-book")
    public RestBean<List<Book>> getSameTypeBook(@RequestParam("book_id") int book_id){
        return RestBean.success(bookService.getSameTypeBookByBookId(book_id));
    }

    //根据书籍ID 获取同作者的书籍
    @PostMapping("/get-same-author-book")
    public RestBean<List<Book>> getSameAuthorBook(@RequestParam("book_id") int book_id){
        return RestBean.success(bookService.getSameAuthorBookByBookId(book_id));
    }
}
