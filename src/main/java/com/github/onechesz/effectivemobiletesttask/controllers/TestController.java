package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.utils.ExceptionResponse;
import com.github.onechesz.effectivemobiletesttask.utils.UserNotAuthenticatedException;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/test")
public class TestController {
    @GetMapping(path = "")
    public Map<String, String> showUserInfo(@NotNull HttpServletRequest httpServletRequest) {
        ExceptionResponse exceptionResponse = (ExceptionResponse) httpServletRequest.getAttribute("exception");

        if (exceptionResponse != null) throw new UserNotAuthenticatedException(exceptionResponse);

        return Map.of("message", "123");
    }

    @Contract("_ -> new")
    @ExceptionHandler
    private @NotNull ResponseEntity<ExceptionResponse> userNotAuthenticatedHandler(@NotNull UserNotAuthenticatedException userNotAuthenticatedException) {
        return new ResponseEntity<>(userNotAuthenticatedException.getExceptionResponse(), HttpStatus.BAD_REQUEST);
    }
}
