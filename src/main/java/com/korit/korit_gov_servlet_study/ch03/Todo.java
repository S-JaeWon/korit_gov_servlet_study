package com.korit.korit_gov_servlet_study.ch03;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Todo {
    private Integer todoId;
    private String title;
    private String content;
    private String username;
}
