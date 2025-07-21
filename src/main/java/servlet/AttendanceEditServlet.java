package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.AttendanceDAO;
import model.entity.Attendance;

public class AttendanceEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // URLのパラメータからidを取得
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("AttendanceListServlet");
            return;
        }

        int id = Integer.parseInt(idParam);
        
        // DAOから該当の勤怠データを取得
        AttendanceDAO dao = new AttendanceDAO();
        Attendance attendance = dao.findById(id);

        if (attendance == null) {
        	// データがなければ一覧へ
            response.sendRedirect("attendance-list");
            return;
        }

        // JSPに渡す
        request.setAttribute("attendance", attendance);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/attendanceEdit.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        
        // パラメータを取得
        int id = Integer.parseInt(request.getParameter("id"));
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime clockIn = LocalDateTime.parse(request.getParameter("clockIn"), formatter);
        LocalDateTime clockOut = LocalDateTime.parse(request.getParameter("clockOut"), formatter);
        LocalDateTime breakStart = LocalDateTime.parse(request.getParameter("breakStart"), formatter);
        LocalDateTime breakEnd = LocalDateTime.parse(request.getParameter("breakEnd"), formatter);
        int overtimeHours = Integer.parseInt(request.getParameter("overtimeHours"));

        // 更新用オブジェクトを作成
        Attendance attendance = new Attendance();
        attendance.setId(id);
        attendance.setEmployeeId(employeeId);
        attendance.setClockIn(clockIn);
        attendance.setClockOut(clockOut);
        attendance.setBreakStart(breakStart);
        attendance.setBreakEnd(breakEnd);
        attendance.setOvertimeHours(overtimeHours);

        // DAOで更新処理
        AttendanceDAO dao = new AttendanceDAO();
        boolean success = dao.update(attendance);

        if (success) {
            response.sendRedirect("AttendanceListServlet");
        } else {
            request.setAttribute("error", "更新に失敗しました。");
            request.setAttribute("attendance", attendance);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/attendanceEdit.jsp");
            dispatcher.forward(request, response);
        }        response.sendRedirect("attendance-list");
    }
}
