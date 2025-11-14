package com.korit.korit_gov_servlet_study.ch06;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ch06/boards")
public class BoardServlet extends HttpServlet {
    private BoardRepository boardRepository;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        boardRepository = BoardRepository.getInstance();
        gson = new GsonBuilder().create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Board> boardList = boardRepository.boardList();

        String json = gson.toJson(boardList);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BoardDto boardDto = gson.fromJson(req.getReader(), BoardDto.class);
        Board board = boardRepository.addboard(boardDto.toEntity());

        Response<Board> boardResponse = Response.<Board>builder()
                .message("게시글 작성 완료")
                .body(board)
                .build();

        String json = gson.toJson(boardResponse);
        resp.getWriter().write(json);
    }

}