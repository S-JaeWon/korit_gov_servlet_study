package com.korit.korit_gov_servlet_study.ch03;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.korit.korit_gov_servlet_study.ch02.Todo;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ch03/users")
public class UserServlet extends HttpServlet {
    private UserRepository userRepository;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        userRepository = UserRepository.getInstance();
        gson = new GsonBuilder().create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");

        List<User> userlist = userRepository.userList();

        SuccessResponse<List<User>> successResponse = SuccessResponse.<List<User>>builder()
                .status(200)
                .message("조회 완료")
                .body(userlist)
                .build();

        String json = gson.toJson(successResponse);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        UserDto userDto = gson.fromJson(request.getReader(), UserDto.class); // json -> dto

        User searchUser = userRepository.searchByUsername(userDto.getUsername());

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");

        if (searchUser != null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(400)
                    .message("이미 존재하는 username 입니다.")
                    .build();
            response.setStatus(400);
            String json = gson.toJson(errorResponse);
            response.getWriter().write(json);
            return;
        }

        User user = userRepository.add(userDto.toEntity());

        SuccessResponse<User> successResponse = SuccessResponse.<User>builder()
                .status(200)
                .message("등록 완료")
                .body(user)
                .build();

        String json = gson.toJson(successResponse);
        response.setStatus(200);
        response.getWriter().write(json);
    }
}