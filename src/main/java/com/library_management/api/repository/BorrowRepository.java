package com.library_management.api.repository;

import com.library_management.api.dto.BorrowDTO;
import com.library_management.api.model.Borrow;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface BorrowRepository {

    List<BorrowDTO> getAllBorrowings();

    List<BorrowDTO> getBorrowingByBookTitle(String title);

    List<BorrowDTO> getBorrowingByUser(Long userId);

    void createBorrowing(Long bookId, Long userId, Date startDate, Date endDate);

    void deleteBorrowing(Long borrowId);
}
