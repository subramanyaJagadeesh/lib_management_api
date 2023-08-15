package com.library_management.api.service;

import com.library_management.api.Model.Borrow;

import java.util.Date;
import java.util.List;

public interface BorrowService {
    List<Borrow> getAllBorrowings();

    List<Borrow> getBorrowingByBookTitle(String title);

    List<Borrow> getBorrowingByUser(Long userId);

    void createBorrowing(Long bookId, Long userId, Date startDate, Date endDate);

    void deleteBorrowing(Long borrowId);
}
