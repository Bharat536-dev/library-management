package com.library.management.service;

import com.library.management.model.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    void deleteBook(Long id);
    List<Book> getAllBooks();
    List<Book> searchBooks(String keyword);


}
