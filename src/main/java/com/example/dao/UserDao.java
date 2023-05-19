package com.example.dao;

import com.example.entity.User;
import com.example.exeption.DaoException;
import com.example.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final UserDao INSTANCE = new UserDao();
    private static final String DELETE_SQL = """
            DELETE FROM user
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO user (fio, phoneNumber, technologies)
            VALUE (?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE user
            SET fio = ?,
                phoneNumber = ?,
                technologies = ?
            WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id,
                   fio,
                   phoneNumber,
                   technologies
            FROM user
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    public UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
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
                user.setId(generatedKeys.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public User findById(int id) {
        try (var connection = ConnectionManager.getConnection();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setInt(1, id);

            User user = null;
            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<User> findAll() {
        try (var connection = ConnectionManager.getConnection();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = prepareStatement.executeQuery();

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public int update(User user) {
        try (var connection = ConnectionManager.getConnection();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, user.getFio());
            prepareStatement.setString(2, user.getPhoneNumber());
            prepareStatement.setString(3, user.getTechnologies());
            prepareStatement.setInt(4, user.getId());

            return prepareStatement.executeUpdate();
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

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("fio"),
                resultSet.getString("phoneNumber"),
                resultSet.getString("technologies")
        );
    }
}
