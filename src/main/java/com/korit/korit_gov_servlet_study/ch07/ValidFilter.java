package com.korit.korit_gov_servlet_study.ch07;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/ch07/*")
public class ValidFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        chain.doFilter(request, response);

    }
}
