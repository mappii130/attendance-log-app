package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションの取得と破棄
        HttpSession session = request.getSession(false); // falseで取得 → セッションがなければnull
        if (session != null) {
            session.invalidate(); // セッションを無効化
        }

        // ログイン画面にリダイレクト
        response.sendRedirect("/WEB-INF/jsp/login.jsp");
    }
}
