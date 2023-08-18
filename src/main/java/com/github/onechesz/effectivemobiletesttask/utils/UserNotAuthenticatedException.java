package com.github.onechesz.effectivemobiletesttask.utils;

public class UserNotAuthenticatedException extends RuntimeException {
    private ExceptionResponse exceptionResponse;

    public UserNotAuthenticatedException() {

    }

    public UserNotAuthenticatedException(ExceptionResponse exceptionResponse) {
        this.exceptionResponse = exceptionResponse;
    }

    public UserNotAuthenticatedException(String message) {
        super(message);
    }

    public ExceptionResponse getExceptionResponse() {
        return exceptionResponse;
    }

    public void setExceptionResponse(ExceptionResponse exceptionResponse) {
        this.exceptionResponse = exceptionResponse;
    }
}
