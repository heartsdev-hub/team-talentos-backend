package com.backend.backend.Exception;

import java.time.LocalDateTime;

public class ErrorResponse<T> {
    private String message;
    private int status;
    private String path;

    public ErrorResponse(String message, int status, String path) {
        this.message = message;
        this.status = status;
        this.path = path;
    }
}
