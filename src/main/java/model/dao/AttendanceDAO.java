package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.entity.Attendance;

public class AttendanceDAO {

    // 勤怠データを登録
    public boolean insert(Attendance attendance) {
        String sql = "INSERT INTO attendance (employee_id, clock_in, clock_out, break_start, break_end, overtime_hours) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, attendance.getEmployeeId());
            stmt.setTimestamp(2, Timestamp.valueOf(attendance.getClockIn()));
            stmt.setTimestamp(3, attendance.getClockOut() != null ? Timestamp.valueOf(attendance.getClockOut()) : null);
            stmt.setTimestamp(4, attendance.getBreakStart() != null ? Timestamp.valueOf(attendance.getBreakStart()) : null);
            stmt.setTimestamp(5, attendance.getBreakEnd() != null ? Timestamp.valueOf(attendance.getBreakEnd()) : null);
            stmt.setInt(6, attendance.getOvertimeHours());

            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 勤怠データを更新
    public boolean update(Attendance attendance) {
        String sql = "UPDATE attendance SET clock_in=?, clock_out=?, break_start=?, break_end=?, overtime_hours=? " +
                     "WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(attendance.getClockIn()));
            stmt.setTimestamp(2, attendance.getClockOut() != null ? Timestamp.valueOf(attendance.getClockOut()) : null);
            stmt.setTimestamp(3, attendance.getBreakStart() != null ? Timestamp.valueOf(attendance.getBreakStart()) : null);
            stmt.setTimestamp(4, attendance.getBreakEnd() != null ? Timestamp.valueOf(attendance.getBreakEnd()) : null);
            stmt.setInt(5, attendance.getOvertimeHours());
            stmt.setInt(6, attendance.getId());

            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 勤怠データを削除
    public boolean delete(int id) {
        String sql = "DELETE FROM attendance WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 社員IDごとの勤怠データをすべて取得
    public List<Attendance> findByEmployeeId(int employeeId) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE employee_id = ? ORDER BY clock_in DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Attendance att = new Attendance();
                att.setId(rs.getInt("id"));
                att.setEmployeeId(rs.getInt("employee_id"));
                att.setClockIn(rs.getTimestamp("clock_in").toLocalDateTime());

                Timestamp out = rs.getTimestamp("clock_out");
                if (out != null) att.setClockOut(out.toLocalDateTime());

                Timestamp brStart = rs.getTimestamp("break_start");
                if (brStart != null) att.setBreakStart(brStart.toLocalDateTime());

                Timestamp brEnd = rs.getTimestamp("break_end");
                if (brEnd != null) att.setBreakEnd(brEnd.toLocalDateTime());

                att.setOvertimeHours(rs.getInt("overtime_hours"));
                list.add(att);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}

