package com.example.service;

import com.example.entity.Book;
import com.example.entity.NewCreateBook;
import com.example.entity.Type;

import java.util.List;

/**
 * @ClassName BookService
 * @Author syp10
 * @Data 2023/5/2 16:52
 */
public interface BookService {

    List<Book> getAllBook();

    int batchDeleteBook(String ids);

    List<Type> getAllType();

    int editBook(Book book);

    int addBook(Book book);

    String addType(String type_name);

    int deleteType(int type_id);

    List<Book> searchBookByTitleOrAuthor(String TitleOrAuthor, String text);

    int getBookUniqueCount();

    int getBookTotalCount();

    int getTypeCount();

    List<NewCreateBook> getNewCreateBook();

    Book getBookById(String bid);

    List<Book> getSameTypeBookByBookId(int book_id);

    List<Book> getSameAuthorBookByBookId(int book_id);
}
