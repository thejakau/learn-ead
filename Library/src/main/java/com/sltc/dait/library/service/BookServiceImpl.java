package com.sltc.dait.library.service;

import com.sltc.dait.library.exception.NoSuchBookExistsException;
import com.sltc.dait.library.model.Book;
import com.sltc.dait.library.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addBook(Book book){
        bookRepo.save(book);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateBook(String isbn, Book book) {
        bookRepo.findById(isbn).orElseThrow(() -> new NoSuchBookExistsException("Book not found for ISBN " + isbn));
        book.setIsbn(isbn);
        bookRepo.save(book);
    }

    @Override
    public Book getBook(String isbn) {
        return bookRepo.findById(isbn).orElseThrow(() -> new NoSuchBookExistsException("Book not found for ISBN " + isbn));
    }
}
