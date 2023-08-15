package com.library_management.api.service.impl;

import com.library_management.api.Model.Book;
import com.library_management.api.repository.BookRepository;
import com.library_management.api.repository.UserRepository;
import com.library_management.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(Optional<Integer> category){
        return bookRepository.getAllBooks(category);
    }

    public Book getBook(Long id){
        return bookRepository.getBook(id);
    }

    public void createBook(String title, String author, Integer category, Long inventorySize, Date publishedDate) {
        bookRepository.createBook(title, author, category, inventorySize, publishedDate);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteBook(id);
    }
}
