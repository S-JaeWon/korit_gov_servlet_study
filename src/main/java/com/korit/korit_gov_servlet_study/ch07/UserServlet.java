package com.korit.korit_gov_servlet_study.ch07;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ch07/users")
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
        String username = req.getParameter("username");

        if (username == null) {
            List<User> userListAll = userService.userGetAll();
            ResponseDto<List<User>> responseDto = ResponseDto.<List<User>>builder()
                    .status(200)
                    .body(userListAll)
                    .build();
            String json = gson.toJson(responseDto);
            resp.getWriter().write(json);
            return;
        }

        User userList = userService.userList(username);
        ResponseDto<?> responseDto;

        if (userList == null) {
            responseDto = ResponseDto.<String>builder()
                    .status(200)
                    .body(username + "은 존재 하지 않습니다.")
                    .build();
        } else {
            responseDto = ResponseDto.<User>builder()
                    .status(200)
                    .message("회원 조회 완료")
                    .body(userList)
                    .build();
        }

        String json = gson.toJson(responseDto);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignupReqDto signupReqDto = gson.fromJson(req.getReader(), SignupReqDto.class);

        boolean searchUser = userService.isDupleUsername(signupReqDto.getUsername());

        if (searchUser) {
            ResponseDto<String> userResponseDto = ResponseDto.<String>builder()
                    .status(400)
                    .message("username이 중복되었습니다.")
                    .body(signupReqDto.getUsername())
                    .build();
            resp.setStatus(400);
            String json = gson.toJson(userResponseDto);
            resp.getWriter().write(json);
            return;
        }

        User user = userService.addUser(signupReqDto);

        ResponseDto<User> userResponseDto = ResponseDto.<User>builder()
                .status(200)
                .message("회원 등록 완료")
                .body(user)
                .build();
        String json = gson.toJson(userResponseDto);
        resp.getWriter().write(json);
    }
}