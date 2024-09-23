package com.sltc.dait.library.service;

import com.sltc.dait.library.model.Book;

public interface BookService {
    public void addBook(Book book);
    public void updateBook(String isbn, Book book);
    public Book getBook(String isbn);
}
