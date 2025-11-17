package com.korit.korit_gov_servlet_study.ch08.user.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.protobuf.Api;
import com.korit.korit_gov_servlet_study.ch08.user.dto.ApiRespDto;
import com.korit.korit_gov_servlet_study.ch08.user.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch08.user.entity.User;
import com.korit.korit_gov_servlet_study.ch08.user.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@WebServlet("/ch08/user")
public class UserServlet extends HttpServlet {
    private UserService userService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String keyword = req.getParameter("keyword");
        ApiRespDto<?> apiRespDto = null;

        if (username != null) {
            Optional<User> searchUser= userService.searchByUsername(username);
            if (searchUser.isPresent()) {
                apiRespDto = ApiRespDto.<User>builder()
                        .status("success")
                        .message(username + " 회원 조회")
                        .body(searchUser.get())
                        .build();
            }
        } else if (keyword != null) {

        } else {

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignupReqDto signupReqDto = objectMapper.readValue(req.getReader(), SignupReqDto.class);

        ApiRespDto<?> apiRespDto;

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
        objectMapper.writeValue(resp.getWriter(), apiRespDto);
    }
}