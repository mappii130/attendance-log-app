package model.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Attendance {
    private int id;
    private int employeeId;
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
    private LocalDateTime breakStart;
    private LocalDateTime breakEnd;
    private int overtimeHours; // 単位：分 or 時間（DBに合わせる）
//    private Employee employee; // Employeeオブジェクトを持たせる

    // コンストラクタ（デフォルト）
    public Attendance() {}

    // コンストラクタ（全項目）
    public Attendance(int id, int employeeId, LocalDateTime clockIn, LocalDateTime clockOut,
                      LocalDateTime breakStart, LocalDateTime breakEnd, int overtimeHours) {
        this.id = id;
        this.employeeId = employeeId;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
        this.overtimeHours = overtimeHours;
    }

    // getter / setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public LocalDateTime getClockIn() { return clockIn; }
    public void setClockIn(LocalDateTime clockIn) { this.clockIn = clockIn; }

    public LocalDateTime getClockOut() { return clockOut; }
    public void setClockOut(LocalDateTime clockOut) { this.clockOut = clockOut; }

    public LocalDateTime getBreakStart() { return breakStart; }
    public void setBreakStart(LocalDateTime breakStart) { this.breakStart = breakStart; }

    public LocalDateTime getBreakEnd() { return breakEnd; }
    public void setBreakEnd(LocalDateTime breakEnd) { this.breakEnd = breakEnd; }

    public int getOvertimeHours() { return overtimeHours; }
    public void setOvertimeHours(int overtimeHours) { this.overtimeHours = overtimeHours; }
    
    // 労働時間（休憩時間を差し引いた総時間）を分単位で返す
    public long getTotalWorkMinutes() {
        if (clockIn != null && clockOut != null) {
            Duration workDuration = Duration.between(clockIn, clockOut);
            Duration breakDuration = Duration.ZERO;

            if (breakStart != null && breakEnd != null) {
                breakDuration = Duration.between(breakStart, breakEnd);
            }

            long baseMinutes = workDuration.minus(breakDuration).toMinutes();
            return baseMinutes + overtimeHours; // ← ここで加算
        }
        return overtimeHours; // 勤務時間がない場合でも overtimeHours が存在する可能性に対応
    }

    // 表示用フォーマット：日付
    public String getDateString() {
        if (clockIn != null) {
            return clockIn.toLocalDate().toString(); // yyyy-MM-dd
        }
        return "";
    }

    // 表示用フォーマット：出勤時刻（HH:mm）
    public String getClockInTimeString() {
        if (clockIn != null) {
            return clockIn.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        return "";
    }

    // 表示用フォーマット：退勤時刻（HH:mm）
    public String getClockOutTimeString() {
        if (clockOut != null) {
            return clockOut.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        return "";
    }

    // 表示用フォーマット：労働時間（hh時間mm分）
    public String getTotalWorkTimeString() {
        long totalMinutes = getTotalWorkMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        return hours + "時間" + minutes + "分";
    }
}
