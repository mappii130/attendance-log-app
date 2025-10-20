package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    
 // 勤怠IDで1件の勤怠データを取得
    public Attendance findById(int id) {
        String sql = "SELECT * FROM attendance WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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
                return att;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
 // 残業集計（年・月ごと・週ごと）
    public Map<Integer, Map<Integer, String>> getOvertimeSummaryByYear(int employeeId, int year) {
        Map<Integer, Map<Integer, String>> result = new LinkedHashMap<>();

        // 1〜12月の枠を用意
        for (int m = 1; m <= 12; m++) {
            Map<Integer, String> weekMap = new LinkedHashMap<>();
            for (int w = 1; w <= 5; w++) {
                weekMap.put(w, "0");
            }
            weekMap.put(99, "0"); // 月合計
            result.put(m, weekMap);
        }

        String sql =
            "SELECT MONTH(clock_in) AS month, " +
            "       WEEK(clock_in, 0) - WEEK(DATE_SUB(clock_in, INTERVAL DAYOFMONTH(clock_in)-1 DAY), 0) + 1 AS week, " +
            "       SUM(overtime_hours) AS total " +
            "FROM attendance " +
            "WHERE employee_id = ? AND YEAR(clock_in) = ? " +
            "GROUP BY month, week " +
            "ORDER BY month, week";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.setInt(2, year);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int month = rs.getInt("month");
                int week = rs.getInt("week");
                int total = rs.getInt("total");

                Map<Integer, String> weekMap = result.get(month);
                if (weekMap != null) {
                    weekMap.put(week, String.valueOf(total));

                    // --- ✅ 月合計を必ず0から加算 ---
                    String currentTotalStr = weekMap.get(99);
                    int currentTotal = 0;
                    if (currentTotalStr != null && !currentTotalStr.equals("null")) {
                        try {
                            currentTotal = Integer.parseInt(currentTotalStr);
                        } catch (NumberFormatException e) {
                            currentTotal = 0;
                        }
                    }

                    int monthTotal = currentTotal + total;
                    weekMap.put(99, String.valueOf(monthTotal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
 // 日付範囲で勤怠データを検索
    public List<Attendance> findByDateRange(int employeeId, String startDate, String endDate) {
        List<Attendance> list = new ArrayList<>();

        String sql = "SELECT * FROM attendance " +
                     "WHERE employee_id = ? AND DATE(clock_in) BETWEEN ? AND ? " +
                     "ORDER BY clock_in ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);

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

