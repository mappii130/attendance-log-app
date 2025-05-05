package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.AttendanceDAO;
import model.entity.Attendance;
import model.entity.Employee;

public class AttendanceListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションからログイン中の社員を取得
        HttpSession session = request.getSession(false);
        Employee employee = (Employee) session.getAttribute("employee");

        if (employee == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // DAOで勤怠情報を取得
        AttendanceDAO dao = new AttendanceDAO();
        List<Attendance> attendanceList = dao.findByEmployeeId(employee.getId());

        // リクエストにセットしてJSPへ転送
        request.setAttribute("attendanceList", attendanceList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/attendanceList.jsp");
        dispatcher.forward(request, response);
    }
}
