package servlet;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            response.sendRedirect("attendance-list");
            return;
        }

        // 日時のフォーマット
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        request.setAttribute("dateValue",
            attendance.getClockIn() != null
                ? attendance.getClockIn().toLocalDate().format(dateFormatter)
                : "");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        request.setAttribute("clockInTime",
            attendance.getClockIn() != null
                ? attendance.getClockIn().toLocalTime().format(timeFormatter)
                : "");

        request.setAttribute("clockOutTime",
            attendance.getClockOut() != null
                ? attendance.getClockOut().toLocalTime().format(timeFormatter)
                : "");

        request.setAttribute("breakStartTime",
            attendance.getBreakStart() != null
                ? attendance.getBreakStart().toLocalTime().format(timeFormatter)
                : "");

        request.setAttribute("breakEndTime",
            attendance.getBreakEnd() != null
                ? attendance.getBreakEnd().toLocalTime().format(timeFormatter)
                : "");

        request.setAttribute("attendance", attendance);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/attendanceEdit.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
     // 日付取得
        String dateStr = request.getParameter("date");
        LocalDate date = null;
        if (dateStr != null && !dateStr.isEmpty()) {
            date = LocalDate.parse(dateStr);
        }

        // 時間取得
        String clockInStr = request.getParameter("clockInTime");
        String clockOutStr = request.getParameter("clockOutTime");
        String breakStartStr = request.getParameter("breakStartTime");
        String breakEndStr = request.getParameter("breakEndTime");

        // null対策しながら変換
        LocalTime clockInTime = (clockInStr != null && !clockInStr.isEmpty()) ? LocalTime.parse(clockInStr) : null;
        LocalTime clockOutTime = (clockOutStr != null && !clockOutStr.isEmpty()) ? LocalTime.parse(clockOutStr) : null;
        LocalTime breakStartTime = (breakStartStr != null && !breakStartStr.isEmpty()) ? LocalTime.parse(breakStartStr) : null;
        LocalTime breakEndTime = (breakEndStr != null && !breakEndStr.isEmpty()) ? LocalTime.parse(breakEndStr) : null;

        // LocalDateTimeに合成
        LocalDateTime clockIn = (clockInTime != null) ? LocalDateTime.of(date, clockInTime) : null;
        LocalDateTime clockOut = (clockOutTime != null) ? LocalDateTime.of(date, clockOutTime) : null;
        LocalDateTime breakStart = (breakStartTime != null) ? LocalDateTime.of(date, breakStartTime) : null;
        LocalDateTime breakEnd = (breakEndTime != null) ? LocalDateTime.of(date, breakEndTime) : null;

        long workMinutes = 0;

	     // 勤務時間
	     if (clockIn != null && clockOut != null) {
	         workMinutes = Duration.between(clockIn, clockOut).toMinutes();
	     }
	
	     // 休憩時間
	     long breakMinutes = 0;
	     if (breakStart != null && breakEnd != null) {
	         breakMinutes = Duration.between(breakStart, breakEnd).toMinutes();
	     }
	
	     // 実働時間
	     long actualWorkMinutes = workMinutes - breakMinutes;
	
	     // 残業（8時間 = 480分）
	     long overtimeMinutes = 0;
	     if (actualWorkMinutes > 480) {
	         overtimeMinutes = actualWorkMinutes - 480;
	     }
	
	     // intに変換
	     int overtimeHours = (int) overtimeMinutes;
        
        Attendance attendance = new Attendance();
        attendance.setId(id);
        attendance.setEmployeeId(employeeId);
        attendance.setClockIn(clockIn);
        attendance.setClockOut(clockOut);
        attendance.setBreakStart(breakStart);
        attendance.setBreakEnd(breakEnd);
        
        attendance.setOvertimeHours(overtimeHours);

        AttendanceDAO dao = new AttendanceDAO();
        boolean success = dao.update(attendance);

        if (success) {
            response.sendRedirect("AttendanceListServlet");
        } else {
            request.setAttribute("error", "更新に失敗しました。");
            request.setAttribute("attendance", attendance);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/attendanceEdit.jsp");
            dispatcher.forward(request, response);
        }
    }
}