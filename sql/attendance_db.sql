-- データベースの作成
CREATE DATABASE IF NOT EXISTS attendance_db;
USE attendance_db;

-- 社員テーブル
CREATE TABLE IF NOT EXISTS employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 勤怠テーブル
CREATE TABLE IF NOT EXISTS attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    clock_in DATETIME NOT NULL,
    clock_out DATETIME,
    break_start DATETIME,
    break_end DATETIME,
    overtime_hours INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

-- 初期データ（例）
INSERT INTO employee (name, email, password) VALUES 
('山田 太郎', 'yamada@example.com', 'password123'),
('田中 花子', 'tanaka@example.com', 'password456');

-- 勤怠データ（山田 太郎: id=1）
INSERT INTO attendance (employee_id, clock_in, clock_out, break_start, break_end, overtime_hours) VALUES
(1, '2025-06-01 09:00:00', '2025-06-01 18:00:00', '2025-06-01 12:00:00', '2025-06-01 13:00:00', 60),
(1, '2025-06-02 09:00:00', '2025-06-02 19:30:00', '2025-06-02 12:00:00', '2025-06-02 13:00:00', 90),
(1, '2025-06-03 08:30:00', '2025-06-03 17:00:00', '2025-06-03 12:00:00', '2025-06-03 12:45:00', 0);

-- 勤怠データ（田中 花子: id=2）
INSERT INTO attendance (employee_id, clock_in, clock_out, break_start, break_end, overtime_hours) VALUES
(2, '2025-06-01 09:15:00', '2025-06-01 18:45:00', '2025-06-01 12:15:00', '2025-06-01 13:15:00', 60),
(2, '2025-06-02 10:00:00', '2025-06-02 19:00:00', '2025-06-02 13:00:00', '2025-06-02 14:00:00', 60),
(2, '2025-06-03 08:50:00', '2025-06-03 17:40:00', '2025-06-03 12:00:00', '2025-06-03 13:00:00', 40);

