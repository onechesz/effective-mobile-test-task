package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.utils.UserNotAuthenticatedException;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/test")
public class TestController {
    @GetMapping(path = "")
    public Map<String, String> showUserInfo(@NotNull HttpServletRequest httpServletRequest) {
        UserNotAuthenticatedException userNotAuthenticatedException = (UserNotAuthenticatedException) httpServletRequest.getAttribute("exception");

        if (userNotAuthenticatedException != null)
            throw userNotAuthenticatedException;

        return Map.of("message", "123");
    }
}
