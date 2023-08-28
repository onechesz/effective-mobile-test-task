package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.dtos.authentication.AuthenticationDTO;
import com.github.onechesz.effectivemobiletesttask.dtos.user.UserDTO;
import com.github.onechesz.effectivemobiletesttask.secutiry.JWTUtil;
import com.github.onechesz.effectivemobiletesttask.services.UserService;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.UserNotAuthenticatedException;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.UserNotRegisteredException;
import com.github.onechesz.effectivemobiletesttask.validators.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
@Tag(name = "Auth", description = "Авторизация и аутентификация")
public class AuthController {
    private final UserValidator userValidator;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserValidator userValidator, UserService userService, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public static void authenticationCheck(@NotNull HttpServletRequest httpServletRequest) {
        UserNotAuthenticatedException userNotAuthenticatedException = (UserNotAuthenticatedException) httpServletRequest.getAttribute("exception");

        if (userNotAuthenticatedException != null)
            throw userNotAuthenticatedException;
    }

    @PostMapping(path = "/register")
    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "JSON Web Token"),
            @ApiResponse(responseCode = "400", description = "Ошибка при регистрации")
    })
    public Map<String, String> performRegistration(@RequestBody @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные о пользователе", required = true, content = @Content(schema = @Schema(implementation = UserDTO.class))) UserDTO userDTO, @NotNull BindingResult bindingResult) {
        checkForRegisterExceptionsAndThrow(bindingResult);

        userValidator.validate(userDTO, bindingResult);

        checkForRegisterExceptionsAndThrow(bindingResult);

        userService.register(userDTO);

        return Map.of("JWT", jwtUtil.generateToken(userDTO.getName()));
    }

    private void checkForRegisterExceptionsAndThrow(@NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessagesBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMessagesBuilder.append(fieldError.getField()).append(" — ").append(fieldError.getDefaultMessage()).append(", ");

            errorMessagesBuilder.setLength(errorMessagesBuilder.length() - 2);

            throw new UserNotRegisteredException(errorMessagesBuilder.toString());
        }
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = UserNotRegisteredException.class)
    private @NotNull ResponseEntity<ExceptionResponse> userNotRegisteredHandler(@NotNull UserNotRegisteredException userNotRegisteredException) {
        return new ResponseEntity<>(new ExceptionResponse(userNotRegisteredException.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/login")
    @Operation(summary = "Вход")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "JSON Web Token"),
            @ApiResponse(responseCode = "403", description = "Неверные данные для входа")
    })
    public Map<String, String> performLogin(@RequestBody @Valid @NotNull @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для входа", required = true, content = @Content(schema = @Schema(implementation = AuthenticationDTO.class))) AuthenticationDTO authenticationDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getName(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw new UserNotAuthenticatedException("Неверные данные для входа");
        }

        return Map.of("JWT", jwtUtil.generateToken(authenticationDTO.getName()));
    }
}
