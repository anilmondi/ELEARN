package com.cts.elearn.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourseNotPurchasedException.class)
    public ResponseEntity<?> handleNotPurchased(CourseNotPurchasedException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(AlreadyReviewedException.class)
    public ResponseEntity<?> handleAlreadyReviewed(AlreadyReviewedException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(Map.of("message", ex.getMessage()));
    }
}
