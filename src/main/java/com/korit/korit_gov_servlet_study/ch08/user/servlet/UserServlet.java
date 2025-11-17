package com.korit.korit_gov_servlet_study.ch08.user.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.Api;
import com.korit.korit_gov_servlet_study.ch08.user.dto.ApiRespDto;
import com.korit.korit_gov_servlet_study.ch08.user.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch08.user.entity.User;
import com.korit.korit_gov_servlet_study.ch08.user.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ch08/user")
public class UserServlet extends HttpServlet {
    private UserService userService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
        gson = new GsonBuilder().create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignupReqDto signupReqDto = gson.fromJson(req.getReader(), SignupReqDto.class); // json -> dto

        ApiRespDto<?> apiRespDto;
        String json;

        if (userService.checkUsername(signupReqDto.getUsername())) {
            apiRespDto = ApiRespDto.<String>builder()
                    .status("failed")
                    .message(signupReqDto.getUsername() + "은 중복 되었습니다.")
                    .body(signupReqDto.getUsername())
                    .build();
        } else {
            User user = userService.addUser(signupReqDto);
            apiRespDto = ApiRespDto.<User>builder()
                    .status("success")
                    .message("회원 등록 완료")
                    .body(user)
                    .build();
        }
        json = gson.toJson(apiRespDto);
        resp.getWriter().write(json);
    }
}