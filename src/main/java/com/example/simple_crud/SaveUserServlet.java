package com.example.simple_crud;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SaveServlet", value = "/SaveServlet")
public class SaveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Getting all the request parameters from
        // index.html
        String fio = request.getParameter("fio");
        int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
        String technologies = request.getParameter("technologies");

        // User object is created
        User user = new User();

        // Collected parameters like name, numberOfPosts and
        // technologiesPreferred are set for the object so
        // that it can be retrieved in other places
        user.setFio(fio);
        user.setPhoneNumber(phoneNumber);
        user.setTechnologies(technologies);

        // Calling save method in GeekUsersDao by passing
        // User
        int status = UsersDao.save(user);

        // This is possible when the record is saved
        // successfully
        if (status > 0) {
            out.print("< p > Добавление прошло успешно! </p >");
            request.getRequestDispatcher("index.html")
                    .include(request, response);
        } else {
            // If there is an issue in saving the record, we
            // need to show this message
            out.println("Ошибка! Не удалось добавить кандидата");
        }

        out.close();
    }
}
