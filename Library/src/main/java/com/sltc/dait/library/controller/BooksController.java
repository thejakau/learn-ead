package com.sltc.dait.library.controller;

import com.sltc.dait.library.model.Book;
import com.sltc.dait.library.repository.BookRepo;
import com.sltc.dait.library.service.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class BooksController {
    private final BookRepo bookRepo;
    private final BookServiceImpl bookService;

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookRepo.findAll();
    }

    @GetMapping("/books/{isbn}")
    public Book getBook(@PathVariable String isbn){
        return bookService.getBook(isbn);
    }

    @PostMapping("/books")
    public void addBook(@RequestBody Book book){
        bookService.addBook(book);
    }

    @PutMapping("/books/{isbn}")
    public void updateBook(@PathVariable String isbn, @RequestBody Book book){
            bookService.updateBook(isbn, book);
    }
}
