package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

        HttpSession session = request.getSession(false);
        Employee employee = (Employee) session.getAttribute("employee");

        if (employee == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int currentYear = LocalDate.now().getYear();

        List<Integer> yearList = new ArrayList<>();
        for (int i = currentYear - 5; i <= currentYear + 1; i++) {
            yearList.add(i);
        }

        String yearParam = request.getParameter("year");
        int year = (yearParam != null) ? Integer.parseInt(yearParam) : currentYear;

        AttendanceDAO dao = new AttendanceDAO();
        Map<Integer, Map<Integer, String>> rawData = dao.getOvertimeSummaryByYear(employee.getId(), year);

        // --- 表示用データに変換 ---
        Map<Integer, Map<Integer, String>> overtimeData = new LinkedHashMap<>();

        for (int month = 1; month <= 12; month++) {
            Map<Integer, String> weekMap = rawData.get(month);
            Map<Integer, String> displayMap = new LinkedHashMap<>();

            int monthTotalMinutes = 0;

            for (int w = 1; w <= 5; w++) {
                String value = (weekMap != null) ? weekMap.get(w) : null;
                int minutes = parseMinutes(value);
                monthTotalMinutes += minutes;
                displayMap.put(w, formatMinutes(value));
            }

            // 月合計（週99）を追加
            if (monthTotalMinutes > 0) {
                displayMap.put(99, formatMinutes(String.valueOf(monthTotalMinutes)));
            } else {
                displayMap.put(99, "-");
            }

            overtimeData.put(month, displayMap);

            // ✅ デバッグ出力で確認
            System.out.println("【DEBUG】" + month + "月の合計: " + monthTotalMinutes + "分 = " + formatMinutes(String.valueOf(monthTotalMinutes)));
        }



        List<Integer> monthList = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            monthList.add(m);
        }

        request.setAttribute("year", year);
        request.setAttribute("yearList", yearList);
        request.setAttribute("monthList", monthList);
        request.setAttribute("overtimeData", overtimeData);
        
        for (Map.Entry<Integer, Map<Integer, String>> entry : overtimeData.entrySet()) {
            System.out.println("【DEBUG】" + entry.getKey() + "月のデータ:");
            for (Map.Entry<Integer, String> w : entry.getValue().entrySet()) {
                System.out.println("　週 " + w.getKey() + " → " + w.getValue());
            }
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/overtimeManage.jsp");
        dispatcher.forward(request, response);
    }

    /** null, "0", "-" → 0 に変換 */
    private int parseMinutes(String minutesStr) {
        if (minutesStr == null || minutesStr.equals("-") || minutesStr.equals("0")) return 0;
        try {
            return Integer.parseInt(minutesStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** 分→"H:mm"形式。0分またはnullなら"-"を返す */
    private String formatMinutes(String minutesStr) {
        int minutes = parseMinutes(minutesStr);
        if (minutes == 0) return "-";
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format("%d:%02d", h, m);
    }
}
