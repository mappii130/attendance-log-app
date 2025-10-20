package servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.AttendanceDAO;
import model.entity.Attendance;
import model.entity.Employee;

public class AttendanceRegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションからログイン中の社員を取得
        HttpSession session = request.getSession(false);
        Employee employee = (Employee) session.getAttribute("employee");

        if (employee == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");  // ボタンのvalueを取得
        LocalDateTime now = LocalDateTime.now();

        AttendanceDAO dao = new AttendanceDAO();
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employee.getId());
        boolean result = false;

        switch (action) {
            case "出勤":
                attendance.setClockIn(now);
                result = dao.insert(attendance);
                break;
            case "退勤":
                // 直近のレコード取得して退勤時間を更新（簡易版）
                var list = dao.findByEmployeeId(employee.getId());
                if (!list.isEmpty()) {
                    Attendance latest = list.get(0);
                    latest.setClockOut(now);
                    result = dao.update(latest);
                }
                break;
            case "休憩開始":
                var list2 = dao.findByEmployeeId(employee.getId());
                if (!list2.isEmpty()) {
                    Attendance latest = list2.get(0);
                    latest.setBreakStart(now);
                    result = dao.update(latest);
                }
                break;
            case "休憩終了":
                var list3 = dao.findByEmployeeId(employee.getId());
                if (!list3.isEmpty()) {
                    Attendance latest = list3.get(0);
                    latest.setBreakEnd(now);
                    result = dao.update(latest);
                }
                break;
            default:
                result = false;
        }

        if (result) {
            response.sendRedirect("AttendanceListServlet");
        } else {
            request.setAttribute("error", "勤怠登録に失敗しました。");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        // 現在の日時を取得
        LocalDateTime now = LocalDateTime.now();

        // 表示用フォーマットを設定
        java.time.format.DateTimeFormatter dateFormatter =
                java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd");
        java.time.format.DateTimeFormatter timeFormatter =
                java.time.format.DateTimeFormatter.ofPattern("H:mm");

        // フォーマットした日付と時刻を JSP に渡す
        request.setAttribute("currentDate", now.format(dateFormatter));
        request.setAttribute("currentTime", now.format(timeFormatter));
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/attendanceRegister.jsp");
        dispatcher.forward(request, response);
    }

}
