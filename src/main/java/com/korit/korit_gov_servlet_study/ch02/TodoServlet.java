package com.korit.korit_gov_servlet_study.ch02;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*
* post 요청시 파라미터 공백 및 null 체크
* get 요청시 쿼리파라미터 없으면 전체조회, 있으면 title 단건 조회,
* 해당 title이 존재 하지 않으면 없다고 출력
* */
@WebServlet("/ch02/todos")
public class TodoServlet extends HttpServlet {
    private List<Todo> todos;

    @Override
    public void init() throws ServletException {
        todos = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String title = request.getParameter("title");
        if (title == null) { // 파라미터가 없음 ex) localhost:8080/ch02/todos/
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            for (Todo todo : todos) {
                response.getWriter().println(todo);
            }
            return;
        }

        List<Todo> foundTodos = todos.stream()
                .filter(todo -> todo.getTitle().equals(title))
                .toList();

        Todo foundTodo = foundTodos.isEmpty() ? null : foundTodos.get(0);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/html");

        if (foundTodo == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("해당 todo가 없습니다.");
            return;
        }

        response.getWriter().println(foundTodo);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String username = request.getParameter("username");

        Todo todo = Todo.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();

        Map<String, String> error = checkTodo(todo);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        if (!error.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(error);
            return;
        }

        todos.add(todo);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Todo 등록 완료");
        for (Todo todo1 : todos) {
            response.getWriter().println(todo1);
        }
    }

    private Map<String, String> checkTodo(Todo todo) {
        Map<String ,String> error = new HashMap<>();

        Arrays.stream(todo.getClass().getDeclaredFields()).forEach(f -> {
            f.setAccessible(true);
            String fieldName = f.getName();

            try {
                Object fValue = f.get(todo);
                if (fValue == null) {
                    throw new RuntimeException();
                }
                if (fValue.toString().isBlank()) {
                    throw new RuntimeException();
                }
            } catch (IllegalAccessException e) {
                System.out.println("필드에 접근 할 수 없습니다.");
            } catch (RuntimeException e) {
                error.put(fieldName, "빈 값일 수 없습니다.");
            }
        });
        return error;
    }

}