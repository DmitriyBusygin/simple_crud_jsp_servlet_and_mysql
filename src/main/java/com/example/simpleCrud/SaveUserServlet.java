package com.example.simpleCrud;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SaveServlet", value = "/SaveServlet")
public class SaveUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Получение переданных параметров с
        // index.html
        String fio = request.getParameter("fio");
        String phoneNumber = request.getParameter("phoneNumber");
        String technologies = request.getParameter("technologies");

        // Создание кандидата
        User user = new User();
        user.setFio(fio);
        user.setPhoneNumber(phoneNumber);
        user.setTechnologies(technologies);

        // Сохранение кандидата в БД
        int status = UsersDao.save(user);

        if (status > 0) {
            out.print("<p> Добавление прошло успешно! </p>");
            request.getRequestDispatcher("index.html").include(request, response);
        } else {
            out.println("Ошибка! Не удалось добавить кандидата");
        }

        out.close();
    }
}
