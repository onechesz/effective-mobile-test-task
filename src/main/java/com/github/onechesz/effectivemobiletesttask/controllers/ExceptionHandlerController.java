package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.utils.ExceptionResponse;
import com.github.onechesz.effectivemobiletesttask.utils.UserNotAuthenticatedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = UserNotAuthenticatedException.class)
    public ResponseEntity<ExceptionResponse> userNotAuthenticatedHandler(@NotNull UserNotAuthenticatedException userNotAuthenticatedException) {
        return new ResponseEntity<>(new ExceptionResponse(userNotAuthenticatedException.getMessage(), System.currentTimeMillis()), HttpStatus.UNAUTHORIZED);
    }
}
