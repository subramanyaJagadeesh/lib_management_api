package com.library_management.api.repository.impl;

import com.library_management.api.Model.Borrow;
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
    private static final String GET_BORROWINGS_BY_USER = "SELECT * FROM borrow WHERE user_id = (:userId)";

    private static final String CREATE_BORROWING = "INSERT into borrow(book_id, user_id, start_date, end_date) values(:bookId, :userId, :startDate, :endDate)";

    private static final String DELETE_BORROWING = "DELETE from borrow WHERE id = (:id)";
    public List<Borrow> getAllBorrowings(){
        return readJdbcTemplate.query(GET_ALL_BORROWINGS, new BorrowMapper());
    }

    public List<Borrow> getBorrowingByBookTitle(String bookName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", bookName);
        return readJdbcTemplate.query(GET_BORROWINGS_BY_BOOK_NAME, params, new BorrowMapper());
    }

    public List<Borrow> getBorrowingByUser(Long userId) {
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

    public static final class BorrowMapper implements RowMapper<Borrow> {
        public Borrow mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Borrow.builder()
                    .id(rs.getLong("id"))
                    .bookId(rs.getLong("book_id"))
                    .userId(rs.getLong("user_id"))
                    .startDate(rs.getDate("start_date"))
                    .endDate(rs.getDate("end_date"))
                    .actualReturnDate(rs.getDate("actual_return_date"))
                    .isDue(rs.getBoolean("is_due"))
                    .build();
        }
    }
}
