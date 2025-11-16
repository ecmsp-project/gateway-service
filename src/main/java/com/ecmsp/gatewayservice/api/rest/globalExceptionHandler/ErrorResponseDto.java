package com.ecmsp.gatewayservice.api.rest.globalExceptionHandler;

import java.time.LocalDateTime;

public class ErrorResponseDto {
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponseDto(String message, String errorCode, String path) {
        this.message = message;
        this.errorCode = errorCode;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}