package model.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("✅ データベースに接続成功しました！");
        } catch (SQLException e) {
            System.out.println("❌ 接続失敗：" + e.getMessage());
        }
    }
}

