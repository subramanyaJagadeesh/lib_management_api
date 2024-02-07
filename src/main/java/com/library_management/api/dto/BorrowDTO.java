package com.library_management.api.dto;

import com.library_management.api.model.Book;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class BorrowDTO {
    private Long id;
    private Long userId;
    private Book book;
    private Date startDate;
    private Date endDate;
    private Date actualReturnDate;
    private Boolean isDue;

}
