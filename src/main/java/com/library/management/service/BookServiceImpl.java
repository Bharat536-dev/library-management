package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.model.BorrowRecord;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowRecordRepository;
import com.library.management.repository.BorrowerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepo;
    private final BorrowRecordRepository borrowRecordRepo;

    @Override
    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        borrowRecordRepo.deleteByBookId(bookId); // delete borrow records first
        bookRepo.deleteById(bookId);
        System.out.println(bookId);// then delete book
    }


    @Override
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        return bookRepo.findByTitleContaining(keyword);
    }
}
