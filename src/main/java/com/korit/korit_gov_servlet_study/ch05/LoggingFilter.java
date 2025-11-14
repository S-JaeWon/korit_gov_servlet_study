package com.korit.korit_gov_servlet_study.ch05;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        System.out.println("[LoggingFilter] 전처리 - 요청 들어옴" + httpServletRequest.getServletPath());
        chain.doFilter(request, response);
        System.out.println("[LoggingFilter] 후처리 - 응답 나가는 중" );
    }
}
