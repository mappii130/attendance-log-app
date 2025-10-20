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

        // 年リスト（IntegerでもよいがJSPで使いやすくStringで渡す場合はStringのままでも可）
        List<Integer> yearList = new ArrayList<>();
        for (int i = currentYear - 5; i <= currentYear + 1; i++) {
            yearList.add(i);
        }

        String yearParam = request.getParameter("year");
        int year = (yearParam != null) ? Integer.parseInt(yearParam) : currentYear;

        AttendanceDAO dao = new AttendanceDAO();
        Map<Integer, Map<Integer, String>> rawData = dao.getOvertimeSummaryByYear(employee.getId(), year);

        // ---- 重要: 表示用 Map を String キーに変換して作る ----
        // overtimeDataStr: Map<"1"-"12", Map<"1"-"5","99", "H:mm" or "-">>
        Map<String, Map<String, String>> overtimeDataStr = new LinkedHashMap<>();

        for (int month = 1; month <= 12; month++) {
            Map<Integer, String> weekMap = rawData.get(month); // rawData のまま
            Map<String, String> displayMap = new LinkedHashMap<>();

            int monthTotalMinutes = 0;

            for (int w = 1; w <= 5; w++) {
                String raw = (weekMap != null) ? weekMap.get(w) : null;
                int minutes = parseMinutes(raw);
                monthTotalMinutes += minutes;
                // 週ごと表示: 0 -> "-" 、それ以外 -> "H:mm"
                displayMap.put(String.valueOf(w), minutes == 0 ? "-" : formatMinutesFromMinutes(minutes));
            }

            // 月合計（99キー） — ここは累計 monthTotalMinutes をそのまま使う
            displayMap.put("99", monthTotalMinutes == 0 ? "-" : formatMinutesFromMinutes(monthTotalMinutes));

            overtimeDataStr.put(String.valueOf(month), displayMap);
        }

        // 月リストは文字列キーで回す（JSPで扱いやすくするため）
        List<String> monthListStr = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            monthListStr.add(String.valueOf(m));
        }

        // JSP に渡す
        request.setAttribute("year", year);
        request.setAttribute("yearList", yearList);
        request.setAttribute("monthList", monthListStr);          // String list
        request.setAttribute("overtimeDataStr", overtimeDataStr); // String-key map

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/overtimeManage.jsp");
        dispatcher.forward(request, response);
    }

    /** null, "-", "0" -> 0 分、それ以外は整数ミニッツとして返す */
    private int parseMinutes(String minutesStr) {
        if (minutesStr == null) return 0;
        minutesStr = minutesStr.trim();
        if (minutesStr.equals("-") || minutesStr.equals("0") || minutesStr.isEmpty()) return 0;
        // ここで minutesStr は数字（DAOが返すのは分の文字列）として想定
        try {
            return Integer.parseInt(minutesStr);
        } catch (NumberFormatException e) {
            // もし既に "H:mm" の形式が入っているなら変換（安全策）
            if (minutesStr.contains(":")) {
                try {
                    String[] parts = minutesStr.split(":");
                    int h = Integer.parseInt(parts[0]);
                    int mm = Integer.parseInt(parts[1]);
                    return h * 60 + mm;
                } catch (Exception ex) {
                    return 0;
                }
            }
            return 0;
        }
    }

    /** minutes (int) -> "H:mm" 表示。例: 230 -> "3:50" */
    private String formatMinutesFromMinutes(int minutes) {
        if (minutes <= 0) return "-";
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format("%d:%02d", h, m);
    }
}
