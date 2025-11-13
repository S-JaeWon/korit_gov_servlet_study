package com.korit.korit_gov_servlet_study.ch03;

import lombok.Builder;

@Builder
public class ErrorResponse {
    private int status;
    private String message;
}
