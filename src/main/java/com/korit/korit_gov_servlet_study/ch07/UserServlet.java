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
            String json = gson.toJson(userListAll);
            resp.getWriter().write(json);
            return;
        }

        User userList = userService.userList(username);
        String json = gson.toJson(userList);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignupReqDto signupReqDto = gson.fromJson(req.getReader(), SignupReqDto.class);

        boolean searchUser = userService.isDupleUsername(signupReqDto.getUsername());

        if (!searchUser) {
            ResponseDto<User> userResponseDto = ResponseDto.<User>builder()
                    .status(200)
                    .message("username이 중복되었습니다.")
                    .body(null)
                    .build();
            resp.setStatus(200);
            String json = gson.toJson(userResponseDto);
            resp.getWriter().write(json);
            return;
        }

        User user = userService.addUser(signupReqDto.toEntity());

        ResponseDto<User> userResponseDto = ResponseDto.<User>builder()
                .status(200)
                .message("회원 등록 완료")
                .body(user)
                .build();
        resp.setStatus(200);
        String json = gson.toJson(userResponseDto);
        resp.getWriter().write(json);
    }
}