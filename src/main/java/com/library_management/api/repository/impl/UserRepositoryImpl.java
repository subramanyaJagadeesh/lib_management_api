package com.library_management.api.repository.impl;

import com.library_management.api.Model.User;
import com.library_management.api.exception.InternalServerException;
import com.library_management.api.exception.ResourceNotFound;
import com.library_management.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    @Qualifier("readNamedJdbcTemplate")
    private NamedParameterJdbcTemplate readjdbcTemplate;

    @Qualifier("writeNamedJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate writeJdbcTemplate;

    private static String GET_ALL_USERS = "SELECT * FROM user where type = (:type)";
    private static String GET_USER = "SELECT * FROM user where id = (:id)";

    private static String CREATE_USER = "INSERT INTO user(first_name, last_name, email, phone, :type) values (:first_name, :last_name, :email, :phone, :type)";

    private static String DELETE_USER = "DELETE from user where user_id = (:user_id)";
    public List<User> getAllUsers(Boolean type) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("type", type);

        List<User> userRes = readjdbcTemplate.query(GET_ALL_USERS, parameters, new UserRowMapper());
        return  userRes;
    }

    public User getUser(Long userId){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", userId);
        try{
            return readjdbcTemplate.queryForObject(GET_USER, parameters, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFound("No such user with id: "+userId+" found");
        }
    }

    public void createUser(String fName, String lName, String phone, String email, Integer type) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("first_name", fName);
        parameter.addValue("last_name", lName);
        parameter.addValue("phone", phone);
        parameter.addValue("email", email);
        parameter.addValue("type", type);

        try{
            writeJdbcTemplate.update(CREATE_USER, parameter);
        } catch(Exception e) {
            throw new InternalServerException("Unable to create new user, error: "+e.getMessage());
        }
    }

    public void deleteUser(Long userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", userId);
        try{
            writeJdbcTemplate.update(DELETE_USER, parameters);
        } catch (Exception e) {
            throw new InternalServerException("Unable to delete user, error: "+e.getMessage());
        }
    }

    public static final class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getLong("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .phone(rs.getString("phone"))
                    .email(rs.getString("email"))
                    .build();
        }
    }
}
