package com.library_management.api.service.impl;

import com.library_management.api.Model.Borrow;
import com.library_management.api.repository.BorrowRepository;
import com.library_management.api.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    BorrowRepository borrowRepository;

    public List<Borrow> getAllBorrowings(){
        return borrowRepository.getAllBorrowings();
    }

    public List<Borrow> getBorrowingByBookTitle(String title) {
        return borrowRepository.getBorrowingByBookTitle(title);
    }

    public List<Borrow> getBorrowingByUser(Long userId) {
        return borrowRepository.getBorrowingByUser(userId);
    }

    public void createBorrowing(Long bookId, Long userId, Date startDate, Date endDate) {
        borrowRepository.createBorrowing(bookId, userId, startDate, endDate);
    };

    public void deleteBorrowing(Long borrowId) {
        borrowRepository.deleteBorrowing(borrowId);
    };
}
