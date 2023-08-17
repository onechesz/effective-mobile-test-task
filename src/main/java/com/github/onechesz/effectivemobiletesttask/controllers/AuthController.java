package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.dtos.UserDTO;
import com.github.onechesz.effectivemobiletesttask.secutiry.JWTUtil;
import com.github.onechesz.effectivemobiletesttask.services.UserService;
import com.github.onechesz.effectivemobiletesttask.utils.ExceptionResponse;
import com.github.onechesz.effectivemobiletesttask.utils.UserNotRegisteredException;
import com.github.onechesz.effectivemobiletesttask.validators.UserValidator;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    private final UserValidator userValidator;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    public AuthController(UserValidator userValidator, UserService userService, JWTUtil jwtUtil) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path = "/register")
    public Map<String, String> performRegistration(@RequestBody @Valid UserDTO userDTO, @NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessagesBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMessagesBuilder.append(fieldError.getField()).append(" — ").append(fieldError.getDefaultMessage());

            throw new UserNotRegisteredException(errorMessagesBuilder.toString());
        }

        userValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessagesBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMessagesBuilder.append(fieldError.getField()).append(" — ").append(fieldError.getDefaultMessage());

            throw new UserNotRegisteredException(errorMessagesBuilder.toString());
        }

        userService.register(userDTO);

        return Map.of("jwt-token", jwtUtil.generateToken(userDTO.getName()));
    }

    @Contract("_ -> new")
    @ExceptionHandler
    private @NotNull ResponseEntity<ExceptionResponse> userNotRegisteredHandler(@NotNull UserNotRegisteredException userNotRegisteredException) {
        return new ResponseEntity<>(new ExceptionResponse(userNotRegisteredException.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
}
