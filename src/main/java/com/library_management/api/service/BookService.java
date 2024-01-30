package com.library_management.api.service;

import com.library_management.api.model.Book;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAllBooks(Optional<Integer> category);

    Book getBook(Long id);

    void createBook(String title, String author, Integer category, Long inventorySize, Date publishedDate);

    void deleteBook(Long id);
}
