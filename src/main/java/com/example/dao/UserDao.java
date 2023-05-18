package com.example.dao;

import com.example.entity.User;
import com.example.exeption.DaoException;
import com.example.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {

    private static final UserDao INSTANCE = new UserDao();
    private static final String DELETE_SQL = "DELETE FROM user WHERE id = ?";
    private static final String SAVE_SQL = """
            INSERT INTO user (fio, phoneNumber, technologies)
            VALUE (?, ?, ?)
            """;

    public UserDao() {
    }

    public User save(User user) {
        try (var connection = ConnectionManager.getConnection();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, user.getFio());
            prepareStatement.setString(2, user.getPhoneNumber());
            prepareStatement.setString(3, user.getTechnologies());

            prepareStatement.executeUpdate();

            var generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt("id"));
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(int id) {
        try (var connection = ConnectionManager.getConnection();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setInt(1, id);

            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
