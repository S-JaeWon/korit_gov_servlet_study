package com.korit.korit_gov_servlet_study.ch02;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/*
* HTTP 프로토콜 메소드
* 1. get -> 조회 | 요청 데이터가 URL에 들어감
* 2. post -> 생성 | 요청 데이터가 HTTP Body에 포함됨
* 3. put -> 수정/생성 | 전체 데이터 전송
* 4. patch -> 부분 수정 | 일부만 전송 함으로 put 보단 효율적
* 5. delete -> 삭제 | 지정된 데이터 삭제
* 6. head -> 존재여부, 메타 데이터 확인
* 7. options -> HTTP 메서드의 존재여부 또는 cors 프리플라이트 요청에 사용
* 8. connect ->
* 9. trace
* */

@WebServlet("/ch02/method")
public class HttpMethodServlet extends HttpServlet {
    Map<String, String> map = new HashMap<>(Map.of(
            "name", "철수",
            "age", "13",
            "address", "부산"
    ));
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GET 요청");
        System.out.println(request.getMethod());
        System.out.println(request.getParameter("mapKey"));
        String mapKey = request.getParameter("mapKey");

        System.out.println(map.get(mapKey));

        //응답
        response.setContentType("text/html");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter out = response.getWriter(); // 문자 출력용
        out.println(map.get(mapKey));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("post 요청 들어옴");

        System.out.println("요청 메서드" + request.getMethod());
        System.out.println("요청 쿼리 파라미터(keyName)" + request.getParameter("keyName"));
        System.out.println("요청 쿼리 파라미터(value)" + request.getParameter("value"));
        map.put(request.getParameter("keyName"), request.getParameter("value"));

        System.out.println(map.toString());

        //응답
        response.setStatus(201);
        response.setContentType("text/plain");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().println("데이터 추가 성공");

    }
}
