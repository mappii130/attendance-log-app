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

        // 勤怠登録情報を生成
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employee.getId());

        // 今の時間を出勤時刻として登録（例：ボタンを押した時点）
        LocalDateTime now = LocalDateTime.now();
        attendance.setClockIn(now);

        // 他の値は未設定（null）でOK。必要であれば追加可
        attendance.setClockOut(null);
        attendance.setBreakStart(null);
        attendance.setBreakEnd(null);
        attendance.setOvertimeHours(0); // 初期値

        // DAOで登録処理
        AttendanceDAO dao = new AttendanceDAO();
        boolean success = dao.insert(attendance);

        // 結果に応じて遷移
        if (success) {
            response.sendRedirect("AttendanceListServlet"); // web.xmlでマッピングしたURLへ
        } else {
            request.setAttribute("error", "勤怠登録に失敗しました。");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/attendanceRegister.jsp");
        dispatcher.forward(request, response);
    }

}
