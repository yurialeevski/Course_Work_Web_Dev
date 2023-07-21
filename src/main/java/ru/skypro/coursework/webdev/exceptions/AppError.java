package ru.skypro.coursework.webdev.exceptions;

import org.springframework.http.HttpStatus;

public class AppError extends RuntimeException{
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public AppError(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
