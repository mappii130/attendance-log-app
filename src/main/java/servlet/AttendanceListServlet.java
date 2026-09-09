package servlet;

import java.io.IOException;
import java.time.LocalDate;
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

        // キャッシュを無効化
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // セッションからログイン中の社員を取得
        HttpSession session = request.getSession(false);

        // セッションが存在しない、またはログイン情報がない場合
        if (session == null || session.getAttribute("employee") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Employee employee = (Employee) session.getAttribute("employee");

        // DAOで勤怠情報を取得
        AttendanceDAO dao = new AttendanceDAO();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        List<Attendance> attendanceList;

        if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
            // 範囲指定がある場合は検索
            attendanceList = dao.findByDateRange(employee.getId(), startDate, endDate);

            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);

        } else {
            // ない場合は表示している月分
            LocalDate today = LocalDate.now();
            String firstDay = today.withDayOfMonth(1).toString();
            String lastDay = today.withDayOfMonth(today.lengthOfMonth()).toString();

            attendanceList = dao.findCurrentMonthByEmployeeId(employee.getId());

            request.setAttribute("startDate", firstDay);
            request.setAttribute("endDate", lastDay);
        }

        // リクエストにセットしてJSPへ転送
        request.setAttribute("attendanceList", attendanceList);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/attendanceList.jsp");

        dispatcher.forward(request, response);
    }
}