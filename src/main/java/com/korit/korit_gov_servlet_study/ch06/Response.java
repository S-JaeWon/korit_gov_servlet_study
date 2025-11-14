package com.korit.korit_gov_servlet_study.ch06;

import lombok.Builder;

@Builder
public class Response<T> {
    private String message;
    private T body;
}
