package servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.AttendanceDAO;
import model.entity.Employee;

public class OvertimeManageServlet extends HttpServlet {
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

        // 年の指定（プルダウンで選ばれた値 or デフォルト：今年）
        String yearParam = request.getParameter("year");
        int year = (yearParam != null) ? Integer.parseInt(yearParam) : java.time.LocalDate.now().getYear();

        // DAOで残業時間データを取得（Map<月, Map<週, 時間>> のイメージ）
        AttendanceDAO dao = new AttendanceDAO();
        Map<Integer, Map<Integer, String>> overtimeData = dao.getOvertimeSummaryByYear(employee.getId(), year);

        // JSPに渡す
        request.setAttribute("year", year);
        request.setAttribute("overtimeData", overtimeData);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/overtimeManage.jsp");
        dispatcher.forward(request, response);
    }
}
