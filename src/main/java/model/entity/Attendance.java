package model.entity;

import java.time.LocalDateTime;

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
}
