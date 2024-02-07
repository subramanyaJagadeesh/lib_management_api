package com.library_management.api.repository.impl;

import com.library_management.api.enums.BookCategory;
import com.library_management.api.model.Book;
import com.library_management.api.exception.InternalServerException;
import com.library_management.api.exception.ResourceNotFound;
import com.library_management.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @Autowired
    @Qualifier("readNamedJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Qualifier("writeNamedJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate writeJdbcTemplate;

    private static String GET_ALL_BOOKS_OF_CATEGORY = "SELECT * FROM book where category = (:category)";
    private static String GET_ALL_BOOKS = "SELECT * FROM book";
    private static String GET_BOOK = "SELECT * FROM book where id = (:id)";

    private static String CREATE_BOOK = "INSERT INTO book(title, author, category, inventory_size, published_date) values (:title, :author, :category, :inventory_size, :published_date)";

    private static String DELETE_BOOK = "DELETE from book where id = (:bookId)";
    public List<Book> getAllBooks(Optional<Integer> category) {
        String query;
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        if(category.isEmpty()){
            query = GET_ALL_BOOKS;
        }
        else {
            query = GET_ALL_BOOKS_OF_CATEGORY;
            parameters.addValue("category", category.get());
        }
        List<Book> bookRes = jdbcTemplate.query(query, parameters, new BookMapper());
        return  bookRes;
    }

    public Book getBook(Long id){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        try{
            return jdbcTemplate.queryForObject(GET_BOOK, parameters, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFound("No such user with id: "+id+" found");
        }
    }

    public void createBook(String title, String author, Integer category, Long inventorySize, Date publishedDate) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("title", title);
        parameter.addValue("author", author);
        parameter.addValue("category", category);
        parameter.addValue("inventory_size", inventorySize);
        parameter.addValue("published_date", publishedDate);

        try{
            writeJdbcTemplate.update(CREATE_BOOK, parameter);
        } catch(Exception e) {
            throw new InternalServerException("Unable to create new user "+e.getMessage());
        }
    }

    public void deleteBook(Long id) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("bookId", id);

        try{
            writeJdbcTemplate.update(DELETE_BOOK, parameter);
        } catch(Exception e) {
            throw new InternalServerException("Unable to create new user "+e.getMessage());
        }
    }

    public static final class BookMapper implements RowMapper<Book> {
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .author(rs.getString("author"))
                    .inventorySize(rs.getLong("inventory_size"))
                    .publishedDate(rs.getDate("published_date"))
                    .img(rs.getString("img"))
                    .description(rs.getString("description"))
                    .category((rs.getInt("category")))
                    .build();
        }
    }
}
