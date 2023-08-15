package com.library_management.api.repository;

import com.library_management.api.Model.Book;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> getAllBooks(Optional<Integer> category);

    Book getBook(Long id);

    void createBook(String title, String author, Integer category, Long inventorySize, Date publishedDate);

    void deleteBook(Long id);
}
