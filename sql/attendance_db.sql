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
