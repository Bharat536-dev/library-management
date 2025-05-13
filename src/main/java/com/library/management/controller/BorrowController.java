package com.library.management.controller;

import com.library.management.model.Book;
import com.library.management.model.BorrowRecord;
import com.library.management.model.Borrower;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowRecordRepository;
import com.library.management.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BorrowController {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @PostMapping("/borrow")
    public String processBorrow(@RequestParam Long bookId,
                                @RequestParam Long borrowerId,
                                Model model) {

        Book book = bookRepository.findById(bookId).orElse(null);
        Borrower borrower = borrowerRepository.findById(borrowerId).orElse(null);

        if (book == null || borrower == null) {
            model.addAttribute("errorMessage", "Invalid book or borrower ID.");
            return "error"; // You must have an error.html in /templates
        }

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setBorrower(borrower);
        record.setBorrowDate(LocalDate.now()); // Dynamic borrow date

        borrowRecordRepository.save(record);

        return "redirect:/borrowed-books"; // Redirect to list borrowed books
    }

    @GetMapping("/borrowed-books")
    public String listBorrowedBooks(Model model) {
        List<BorrowRecord> records = borrowRecordRepository.findAll();
        records.forEach(r -> System.out.println("Borrow Date: " + r.getBorrowDate()));
        model.addAttribute("borrowRecords", records);
        return "borrowed_books";
    }


    @GetMapping("/borrow")
    public String showBorrowForm(Model model) {
        model.addAttribute("borrowers", borrowerRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        return "borrow_form"; // Thymeleaf form for borrowing a book
    }

    @GetMapping("/borrowers")
    public String getBorrowers(Model model) {
        List<Borrower> borrowers = borrowerRepository.findAll();
        model.addAttribute("borrowers", borrowers);
        return "borrowers"; // This refers to borrowers.html
    }

    @GetMapping("/borrowers/add")
    public String showAddBorrowerForm(Model model) {
        model.addAttribute("borrower", new Borrower());
        return "add-borrower";
    }

    @PostMapping("/borrowers/add")
    public String addBorrower(@ModelAttribute Borrower borrower) {
        borrowerRepository.save(borrower);
        return "redirect:/borrowers";
    }

}
