package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // データベース接続に使う情報（環境に合わせて変更）
    private static final String URL = "jdbc:mysql://localhost:3306/attendance_db?serverTimezone=Asia/Tokyo";
    private static final String USER = "root";
    private static final String PASSWORD = "*******"; // ← 自分のパスワードに変更してください

    // 接続を取得するための共通メソッド
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQLドライバをロード
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
