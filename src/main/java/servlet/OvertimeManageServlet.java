package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

        // 現在の年
        int currentYear = LocalDate.now().getYear();

        // プルダウンに表示する年リスト（例: 過去5年 + 今年 + 来年）
        List<Integer> yearList = new ArrayList<>();
        for (int i = currentYear - 5; i <= currentYear + 1; i++) {
            yearList.add(i);
        }

        // リクエストされた年（未指定なら今年）
        String yearParam = request.getParameter("year");
        int year = (yearParam != null) ? Integer.parseInt(yearParam) : currentYear;

        // DAOで残業時間データを取得（Map<月, Map<週, 時間>>）
        AttendanceDAO dao = new AttendanceDAO();
        Map<Integer, Map<Integer, String>> overtimeData = dao.getOvertimeSummaryByYear(employee.getId(), year);

        // 1～12月のリストを作成
        List<Integer> monthList = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            monthList.add(m);
        }

        // JSP に渡す
        request.setAttribute("year", year);
        request.setAttribute("yearList", yearList);
        request.setAttribute("monthList", monthList);
        request.setAttribute("overtimeData", overtimeData);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/overtimeManage.jsp");
        dispatcher.forward(request, response);
    }
}
