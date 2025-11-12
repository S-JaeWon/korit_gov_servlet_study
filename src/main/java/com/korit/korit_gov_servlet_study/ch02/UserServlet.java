package com.korit.korit_gov_servlet_study.ch02;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/ch02/users")
public class UserServlet extends HttpServlet {
    private List<User> users;

    @Override
    public void init() throws ServletException {
        users = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String username = request.getParameter("username");

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        for(User user : users) {
            if (user.getName().equals(username)) {
                response.getWriter().println(user);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.getWriter().println(username + " 은 존재하지 않습니다.");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        User user = User.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .build();

        Map<String ,String> error = validUser(user);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        if (!error.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(error);
            return;
        }

        users.add(user);
        System.out.println(users);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("사용자 등록 완료");
    }

    private Map<String, String> validUser(User user) {
        Map<String ,String> error = new HashMap<>();

        //user 객체의 선언된 모든 필드(접근제어자 무관)를 스트림으로 순회
        Arrays.stream(user.getClass().getDeclaredFields()).forEach(f -> {
            f.setAccessible(true); //private 이어도 접근 허용
            String fieldName = f.getName();
            System.out.println(fieldName);

            try {
                //리플렉션으로 필드값 꺼내기
                Object fieldValue = f.get(user);
                System.out.println(fieldValue);
                if (fieldValue == null) { //null 값 체크
                    throw new RuntimeException();
                }
                if (fieldValue.toString().isBlank()) { //빈 값 체크
                    throw new RuntimeException();
                }
            } catch (IllegalAccessException e) { //필드 접근 권한 문제
                System.out.println("필드에 접근 할 수 없습니다.");
            } catch (RuntimeException e) { //위 에러 메세지 받아서 출력
                error.put(fieldName, "빈 값일 수 없습니다.");
            }
        });

        return error;
    }
}