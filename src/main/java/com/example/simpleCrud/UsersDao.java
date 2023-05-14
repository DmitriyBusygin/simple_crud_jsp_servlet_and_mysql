package com.example.simpleCrud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {
    private static String jdbcURL = "jdbc:mysql://localhost:3306/simple_crud_jsp_servlet_and_mysql";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "Admin12345";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            System.out.println("Message.. " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Message.. " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    // Rest of the methods like Save,
    // Update, Delete etc., should come here
    public static int save(User user) {
        int status = 0;
        try {
            Connection con = UsersDao.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "insert into users(fio, phoneNumber, technologies) values (?,?,?)");
            ps.setString(1, user.getFio());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getTechnologies());

            status = ps.executeUpdate();

            con.close();
        } catch (Exception ex) {
            System.out.println("Message.." + ex.getMessage());
            ex.printStackTrace();
        }
        return status;
    }

    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try {
            Connection con = UsersDao.getConnection();
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
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
