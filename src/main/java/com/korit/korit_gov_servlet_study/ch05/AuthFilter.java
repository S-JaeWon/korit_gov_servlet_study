package com.korit.korit_gov_servlet_study.ch05;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        System.out.println("[AuthFilter] 전처리 - 로그인 검증");

        Object principal = httpReq.getSession().getAttribute("principal");

        if (principal == null) {
            System.out.println("[AuthFilter] 로그인 안됨 -> 리다이렉트");
            httpResp.sendRedirect(httpReq.getContextPath() + "/ch05/login");
            return;
        }

        chain.doFilter(request, response);
        System.out.println("[AuthFilter] 후처리 - 로그인 된 사용자의 응답처리");
    }
}
