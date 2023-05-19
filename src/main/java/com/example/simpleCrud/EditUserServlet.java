package com.example.simpleCrud;

import com.example.dao.UserDao;
import com.example.entity.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EditServlet", value = "/EditServlet")
public class EditUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<a href='ViewServlet'>Посмотреть весь список кандидатов</a>");
        out.println("<h1>Редактирование кандидата</h1>");

        // Получить индендификатор из запроса
        // Получить данные из БД по id
        int id = Integer.parseInt(request.getParameter("id"));
        User user = UserDao.getInstance().findById(id);

        out.print("<form action='EditServlet' method='post'>");
        out.print("<table>");
        out.print("<tr><td></td><td><input type='hidden' name='id' value='" + user.getId() + "'/></td></tr>");
        out.print("<tr><td>ФИО:</td><td><input type='text' name='fio' value='" + user.getFio() + "'/></td></tr>");
        out.print("<tr><td>Номер телефона:</td><td><input type='text' name='phoneNumber' value='" + user.getPhoneNumber() + "'/></td></tr>");
        out.print("<tr><td>Список технологий:</td><td><input type='text' name='technologies' value='" + user.getTechnologies() + "'/></td></tr>");

        out.print("<tr><td colspan='2'><input type='submit' value='Сохранить изменения'/></td></tr>");
        out.print("</table>");
        out.print("</form>");

        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        String fio = request.getParameter("fio");
        String phoneNumber = request.getParameter("phoneNumber");
        String technologies = request.getParameter("technologies");

        User user = new User();
        user.setId(id);
        user.setFio(fio);
        user.setPhoneNumber(phoneNumber);
        user.setTechnologies(technologies);

        // Вызвать метод сохранения данных
        int status = UserDao.getInstance().update(user);
        if (status > 0) {
            response.sendRedirect("ViewServlet");
        } else {
            out.println("Внимание! Ошибка при обновлении данных.");
        }

        out.close();
    }
}