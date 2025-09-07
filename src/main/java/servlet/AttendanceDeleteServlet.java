package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.AttendanceDAO;

public class AttendanceDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("AttendanceListServlet");
            return;
        }

        int id = Integer.parseInt(idParam);
        AttendanceDAO dao = new AttendanceDAO();
        boolean success = dao.delete(id);

        if (success) {
            response.sendRedirect("AttendanceListServlet");
        } else {
            request.setAttribute("error", "削除に失敗しました。");
            request.getRequestDispatcher("/WEB-INF/jsp/attendanceEdit.jsp").forward(request, response);
        }
    }
}
