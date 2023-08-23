package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.FileNotCreatedException;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.FileNotDeletedException;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.UserNotAuthenticatedException;
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

    @ExceptionHandler(value = FileNotCreatedException.class)
    public ResponseEntity<ExceptionResponse> fileNotCreatedExceptionHandler(@NotNull FileNotCreatedException fileNotCreatedException) {
        return new ResponseEntity<>(new ExceptionResponse(fileNotCreatedException.getMessage(), System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = FileNotDeletedException.class)
    public ResponseEntity<ExceptionResponse> fileNotDeletedExceptionHandler(@NotNull FileNotDeletedException fileNotDeletedException) {
        return new ResponseEntity<>(new ExceptionResponse(fileNotDeletedException.getMessage(), System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
