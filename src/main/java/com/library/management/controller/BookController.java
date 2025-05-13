package com.library.management.controller;

import com.library.management.model.Book;
import com.library.management.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "home";  // home.html
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";  // add-book.html
    }

    @PostMapping("/add")
    public String saveBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/";
    }

//    @GetMapping("/delete/{id}")
//    public String deleteBook(@PathVariable Long id) {
//        bookService.deleteBook(id);
//        return "redirect:/";
//    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        model.addAttribute("books", bookService.searchBooks(keyword));
        return "home";
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }


}
