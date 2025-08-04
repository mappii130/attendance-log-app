package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.entity.Employee;

public class EmployeeDAO {

    // ログイン認証処理
    public Employee login(String email, String password) {
        Employee employee = null;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM employee WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPassword(rs.getString("password")); // セキュリティ上はあまり返さない方が良いです
            }
        } catch (Exception e) {
        	 e.printStackTrace();
        }

        return employee;
    }

    // ID指定で社員情報を取得（例）
    public Employee findById(int id) {
        Employee employee = null;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM employee WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employee;
    }
}
