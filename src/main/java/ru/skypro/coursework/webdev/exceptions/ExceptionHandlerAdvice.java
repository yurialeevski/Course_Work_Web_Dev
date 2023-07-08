package ru.skypro.coursework.webdev.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.coursework.webdev.service.DtoService;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
    @ExceptionHandler(AppError.class)
    public ResponseEntity<Object> handleException(AppError appError) {
        logger.error("Executed ExceptionHandlerAdvice");
        return ResponseEntity.status(appError.getHttpStatus()).body(appError.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<?> handleIOException(IOException ioException) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
