package com.example.simple_crud;

import java.sql.*;

public class UsersDao {
    private static String jdbcURL = "jdbc:mysql://localhost:3306/simple_crud_jsp_servlet_and_mysql";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "Admin12345";

    public static Connection getConnection() {
        Connection con = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            System.out.println("Message.. " + e.getMessage());
            e.printStackTrace();
        }
//        catch (ClassNotFoundException e) {
//            System.out.println("Message.. " + e.getMessage());
//            e.printStackTrace();
//        }
        return con;
    }
    // Rest of the methods like Save,
    // Update, Delete etc., should come here

    public static int save(User user) {
        int status = 0;
        try {
            Connection con = UsersDao.getConnection();
            // Using PreparedStatement, we can execute any SQL
            // statement In order to save the record, we need to
            // insert the details in 'users' table "e" is an
            // object of type "GeekUsers" which exactly resembles
            // geekusers table From html page, the values has
            // been passed to a servlet and from there, this
            // method is called
            PreparedStatement ps = con.prepareStatement(
                    "insert into users(fio, phoneNumber, technologies) values (?,?,?)");
            ps.setString(1, user.getFio());
            ps.setInt(2, user.getPhoneNumber());
            ps.setString(3, user.getTechnologies());

            status = ps.executeUpdate();

            con.close();
        } catch (Exception ex) {
            System.out.println("Message.." + ex.getMessage());
            ex.printStackTrace();
        }

        return status;
    }

}

