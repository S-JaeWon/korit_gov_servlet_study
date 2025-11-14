package com.korit.korit_gov_servlet_study.ch05;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ch05/mypage/home")
public class MypageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("마이 페이지 요청 들어옴");
        resp.setContentType("text/plain");
        resp.getWriter().write("마이페이지 입니다.");
    }


}