package com.example.simpleCrud;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ViewServlet", value = "/ViewServlet")
public class ViewUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();
        printWriter.println("<a href='index.html'>Добавить нового кандидата</a>");
        printWriter.println("<h1>Список кандидатов</h1>");

        List<User> list = UsersDao.getAllUsers();

        printWriter.print("<table border='1' bordercolor='#009879' width='50%'");
        printWriter.print("<tr><th>Id</th><th>ФИО</th><th>Номер телефона</th><th>Список технологий</th><th>Редактировать</th><th>Удалить</th></tr>");
        for (User user : list) {
            // Каждая строчка идендифицируется с помощью ее идендификатора
            // следовательно, при нажатии "Редактировать" отправлятеся запрос с id строки
            // <a href='EditServlet?id=" + user.getId() + "'>edit</a>
            // <a href='EditServlet?id=1'>edit</a>
            // при нажатии "Удалить" отправляется запрос
            // <a href='DeleteServlet?id=" + user.getId() + "'>delete</a>
            // <a href='DeleteServlet?id=1'>delete</a>
            printWriter.print("<tr><td >" + user.getId() + "</td><td>" + user.getFio() + "</td><td>" + user.getPhoneNumber() + "</td><td>" + user.getTechnologies() + "</td><td><a href='EditServlet?id=" + user.getId() + "'>редактировать</a></td><td><a href='DeleteServlet?id=" + user.getId() + "'>удалить</a></td></tr>");
        }
        printWriter.print("</table>");

        printWriter.close();
    }
}