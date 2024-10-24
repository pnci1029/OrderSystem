package com.example.orderSystem.util;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter @Builder
public class Response<T>{
    private HttpStatus httpStatus;
    private String message;
    private int statusNumber;
    private T data;

    public static <T> Response<T> result(T data, HttpStatus status, String message) {
        return new Response<>(status, message, status.value(), data);
    }

    public static <T> Response<T> result(T data, HttpStatus status) {
        return result(data, status, status.name());
    }

    public static <T> Response<T> ok(T data) {
        return result(data, HttpStatus.OK);
    }

    public static <T> Response<T> ok() {
        return result(null, HttpStatus.OK);
    }
}
