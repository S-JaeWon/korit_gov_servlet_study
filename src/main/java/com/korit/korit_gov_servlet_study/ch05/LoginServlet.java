package com.korit.korit_gov_servlet_study.ch05;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ch05/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("로그인페이지 요청 들어옴");
        resp.setContentType("text/plain");
        resp.getWriter().write("로그인 페이지 입니다.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean checkUser = "test".equals(username) && "1234".equals(password);

        if (!checkUser) {
            resp.getWriter().write("로그인 실패");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("principal", username);

        resp.sendRedirect(req.getContextPath() + "/ch05/mypage/home");
    }
}