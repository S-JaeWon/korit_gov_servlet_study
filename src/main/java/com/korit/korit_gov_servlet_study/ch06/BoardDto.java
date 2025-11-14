package com.korit.korit_gov_servlet_study.ch06;

public class BoardDto {
    private String title;
    private String content;
    private String username;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();
    }
}
