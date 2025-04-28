package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.EmployeeDAO;
import model.entity.Employee;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 文字化け対策
        request.setCharacterEncoding("UTF-8");

        // フォームの入力値を取得
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // DAOを使ってログイン認証
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.login(email, password);

        if (employee != null) {
            // 認証成功 → セッションに保存
            HttpSession session = request.getSession();
            session.setAttribute("employee", employee);

            // 勤怠一覧などへリダイレクト
            response.sendRedirect("attendanceList.jsp");
        } else {
            // 認証失敗 → エラーメッセージをセットして戻す
            request.setAttribute("error", "メールアドレスまたはパスワードが違います。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            dispatcher.forward(request, response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(request, response);
    }

}
