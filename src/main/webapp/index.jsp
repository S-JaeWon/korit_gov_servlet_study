<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #121212; /* 다크 배경 */
            color: #f5f5f5;            /* 밝은 글자 색 */
            font-family: Arial, sans-serif;
        }

        a {
            color: #80cbc4;            /* 링크 색 */
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        h1 {
            padding: 16px;
        }
    </style>
</head>
<body>
<h1><%= "Hello" %></h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>
