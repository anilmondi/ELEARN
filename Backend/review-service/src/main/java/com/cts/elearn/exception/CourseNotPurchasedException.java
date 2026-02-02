package com.cts.elearn.exception;

public class CourseNotPurchasedException extends RuntimeException {
    public CourseNotPurchasedException(String msg) {
        super(msg);
    }
}
