package com.example.service.Impl;

import com.example.entity.Book;
import com.example.entity.NewCreateBook;
import com.example.entity.Type;
import com.example.mapper.BookMapper;
import com.example.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName BookServiceImpl
 * @Description
 * @Author su
 * @Date 2023/5/2 16:52
 */
@Service
public class BookServiceImpl implements BookService {

    @Resource
    BookMapper bookMapper;

    @Override
    public List<Book> getAllBook() {
        return bookMapper.getAllBook();
    }

    @Override
    public int batchDeleteBook(String ids) {
        return bookMapper.batchDeleteBookByIds(ids);
    }

    @Override
    public List<Type> getAllType() {
        return bookMapper.getAllType();
    }

    @Override
    public int editBook(Book book) {
        return bookMapper.editBookById(
               book.getBid(),
               book.getTitle(),
               book.getAuthor(),
               book.getDesc(),
               bookMapper.getTypeIdByTypeName(book.getType_name()),
               book.getNums(),
               book.getCover_url()
       );
    }

    @Override
    public int addBook(Book book) {
        return bookMapper.addBook(
                book.getTitle(),
                book.getAuthor(),
                book.getDesc(),
                bookMapper.getTypeIdByTypeName(book.getType_name()),
                book.getNums(),
                book.getCover_url()
        );
    }


    @Override
    public String addType(String type_name) {
        if (bookMapper.countTypeByTypeName(type_name) == 1){
            return "您要添加的书籍类别已存在";
        }else {
            int result = bookMapper.addType(type_name);
            if (result == 1) return null;
            else return "内部错误，请联系管理员";
        }

    }

    @Override
    public int deleteType(int type_id) {
        return bookMapper.deleteTypeById(type_id);
    }

    @Override
    public List<Book> searchBookByTitleOrAuthor(String TitleOrAuthor, String text) {
        if (Objects.equals(TitleOrAuthor, "title")){
            return bookMapper.selectBookByTitle(text);
        } else if (Objects.equals(TitleOrAuthor, "author")) {
            return bookMapper.selectBookByAuthor(text);
        }else return null;
    }

    @Override
    public int getBookUniqueCount() {
        return bookMapper.getBookUniqueCount();
    }

    @Override
    public int getBookTotalCount() {
        return bookMapper.getBookTotalCount();
    }

    @Override
    public int getTypeCount() {
        return bookMapper.getTypeCount();
    }

    @Override
    public List<NewCreateBook> getNewCreateBook() {
        return bookMapper.getNewCreateBook();
    }

    @Override
    public Book getBookById(String bid) {
        return bookMapper.selectBookByBid(bid);
    }

    @Override
    public List<Book> getSameTypeBookByBookId(int book_id) {
        int type_id = bookMapper.getTypeIdByBookId(book_id);
        return bookMapper.getBookByTypeId(type_id, book_id);
    }

    @Override
    public List<Book> getSameAuthorBookByBookId(int book_id) {
        String author = bookMapper.getAuthorByBookId(book_id);
        return bookMapper.getBookByAuthor(author, book_id);
    }
}
