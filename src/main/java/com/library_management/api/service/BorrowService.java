package com.library_management.api.service;

import com.library_management.api.dto.BorrowDTO;
import com.library_management.api.model.Borrow;

import java.util.Date;
import java.util.List;

public interface BorrowService {
    List<BorrowDTO> getAllBorrowings();

    List<BorrowDTO> getBorrowingByBookTitle(String title);

    List<BorrowDTO> getBorrowingByUser(Long userId);

    void createBorrowing(Long bookId, Long userId, Date startDate, Date endDate);

    void deleteBorrowing(Long borrowId);
}
