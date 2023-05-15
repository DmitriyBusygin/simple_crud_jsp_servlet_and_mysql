package com.example.simpleCrud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/simple_crud_jsp_servlet_and_mysql";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "Admin12345";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Message.. " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    // Рестовые сервисы по Create, Read, Update, Delete
    public static int save(User user) {
        int status = 0;
        try (Connection con = UsersDao.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "insert into users(fio, phoneNumber, technologies) values (?,?,?)");
            ps.setString(1, user.getFio());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getTechnologies());

            status = ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Message.." + ex.getMessage());
            ex.printStackTrace();
        }
        return status;
    }

    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (Connection con = UsersDao.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "select * from users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setFio(rs.getString(2));
                user.setPhoneNumber(rs.getString(3));
                user.setTechnologies(rs.getString(4));
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void delete(int id) {
        try (Connection con = UsersDao.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE from users WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Message.." + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static User getUsersById(int id) {
        User user = new User();
        try (Connection con = UsersDao.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * from users WHERE id=?");
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setFio(resultSet.getString(2));
                user.setPhoneNumber(resultSet.getString(3));
                user.setTechnologies(resultSet.getString(4));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public static int update(User user) {
        int status = 0;
        try (Connection con = UsersDao.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE users " +
                            "SET fio = ?," +
                            "phoneNumber = ?," +
                            "technologies = ?" +
                            "WHERE id = ?");
            ps.setString(1, user.getFio());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getTechnologies());
            ps.setInt(4, user.getId());

            status = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }
}
