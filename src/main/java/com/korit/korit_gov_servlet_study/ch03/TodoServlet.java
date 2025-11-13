package com.korit.korit_gov_servlet_study.ch03;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/ch03/todos")
public class TodoServlet extends HttpServlet {
    private TodoRepository todoRepository;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        todoRepository = TodoRepository.getInstance();
        gson = new GsonBuilder().create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");

        List<Todo> todoList = todoRepository.todoList();

        SuccessResponse<List<Todo>> successResponse = SuccessResponse.<List<Todo>>builder()
                .status(200)
                .message("조회 완료")
                .body(todoList)
                .build();

        String json = gson.toJson(successResponse);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        TodoDto todoDto = gson.fromJson(request.getReader(), TodoDto.class); // json -> dto

        Todo searchTodo = todoRepository.searchByTitle(todoDto.getTitle());

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");

        if (searchTodo != null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(400)
                    .message("중복된 제목입니다.")
                    .build();
            response.setStatus(400);
            String json = gson.toJson(errorResponse);
            response.getWriter().write(json);
            return;
        }

        Todo todo = todoRepository.addTodo(todoDto.toEntity());

        SuccessResponse<Todo> successResponse = SuccessResponse.<Todo>builder()
                .status(200)
                .message("등록 완료")
                .body(todo)
                .build();

        String json = gson.toJson(successResponse);
        response.setStatus(200);
        response.getWriter().write(json);
    }
}