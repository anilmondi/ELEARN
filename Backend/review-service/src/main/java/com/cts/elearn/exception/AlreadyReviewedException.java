package com.cts.elearn.exception;

public class AlreadyReviewedException extends RuntimeException {
    public AlreadyReviewedException(String msg) {
        super(msg);
    }
}
