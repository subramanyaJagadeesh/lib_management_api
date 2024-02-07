package com.library_management.api.repository.impl;

import com.library_management.api.dto.BorrowDTO;
import com.library_management.api.model.Book;
import com.library_management.api.model.Borrow;
import com.library_management.api.exception.InternalServerException;
import com.library_management.api.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class BorrowRepositoryImpl implements BorrowRepository {

    @Autowired
    @Qualifier("readNamedJdbcTemplate")
    NamedParameterJdbcTemplate readJdbcTemplate;

    @Autowired
    @Qualifier("writeNamedJdbcTemplate")
    NamedParameterJdbcTemplate writeJdbcTemplate;

    private static String GET_ALL_BORROWINGS = "SELECT * FROM borrow";
    private static final String GET_BORROWINGS_BY_BOOK_NAME = "SELECT * FROM borrow bo inner join book b on b.id = bo.book_id WHERE b.title = (:title)";
    private static final String GET_BORROWINGS_BY_USER = "SELECT bw.id, bw.user_id, bw.start_date, bw.end_date, bw.actual_return_date, bw.is_due, bo.id as book_id, bo.title, bo.author, bo.inventory_size as inventory, bo.img FROM borrow bw inner join book bo on bw.book_id = bo.id WHERE user_id = (:userId)";

    private static final String CREATE_BORROWING = "INSERT into borrow(book_id, user_id, start_date, end_date) values(:bookId, :userId, :startDate, :endDate)";

    private static final String DELETE_BORROWING = "DELETE from borrow WHERE id = (:id)";
    public List<BorrowDTO> getAllBorrowings(){
        return readJdbcTemplate.query(GET_ALL_BORROWINGS, new BorrowMapper());
    }

    public List<BorrowDTO> getBorrowingByBookTitle(String bookName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", bookName);
        return readJdbcTemplate.query(GET_BORROWINGS_BY_BOOK_NAME, params, new BorrowMapper());
    }

    public List<BorrowDTO> getBorrowingByUser(Long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        return readJdbcTemplate.query(GET_BORROWINGS_BY_USER, params, new BorrowMapper());
    }

    public void createBorrowing(Long bookId, Long userId, Date startDate, Date endDate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bookId", bookId);
        params.addValue("userId", userId);
        params.addValue("startDate", startDate);
        params.addValue("endDate", endDate);
        try{
            writeJdbcTemplate.update(CREATE_BORROWING, params);
        } catch(Exception e) {
            throw new InternalServerException("Unable to create a borrowing, error: "+e.getMessage());
        }
    }

    public void deleteBorrowing(Long borrowId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", borrowId);

        try{
            writeJdbcTemplate.update(DELETE_BORROWING, params);
        } catch(Exception e) {
            throw new InternalServerException("Unable to delete a borrowing, error: "+e.getMessage());
        }
    }

    public static final class BorrowMapper implements RowMapper<BorrowDTO> {
        public BorrowDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return BorrowDTO.builder()
                    .id(rs.getLong("id"))
                    .book(Book.builder()
                            .id(rs.getLong("book_id"))
                            .author(rs.getString("author"))
                            .title(rs.getString("title"))
                            .inventorySize(rs.getLong("inventory"))
                            .img(rs.getString("img"))
                            .build())
                    .userId(rs.getLong("user_id"))
                    .startDate(rs.getDate("start_date"))
                    .endDate(rs.getDate("end_date"))
                    .actualReturnDate(rs.getDate("actual_return_date"))
                    .isDue(rs.getBoolean("is_due"))
                    .build();
        }
    }
}
