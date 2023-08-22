package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.dtos.post.PostDTOI;
import com.github.onechesz.effectivemobiletesttask.dtos.post.PostDTOO;
import com.github.onechesz.effectivemobiletesttask.secutiry.UserDetails;
import com.github.onechesz.effectivemobiletesttask.services.PostService;
import com.github.onechesz.effectivemobiletesttask.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<HttpStatus> performCreation(HttpServletRequest httpServletRequest, @Valid PostDTOI postDTOI, @NotNull BindingResult bindingResult) throws IOException {
        authenticationCheck(httpServletRequest);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessagesBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMessagesBuilder.append(fieldError.getField()).append(" — ").append(fieldError.getDefaultMessage()).append(", ");

            errorMessagesBuilder.setLength(errorMessagesBuilder.length() - 2);

            throw new PostNotCreatedException(errorMessagesBuilder.toString());
        }

        postService.create(postDTOI, ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity());

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = PostNotCreatedException.class)
    private @NotNull ResponseEntity<ExceptionResponse> postNotCreatedExceptionHandler(@NotNull PostNotCreatedException postNotCreatedException) {
        return new ResponseEntity<>(new ExceptionResponse(postNotCreatedException.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = IOException.class)
    private @NotNull ResponseEntity<ExceptionResponse> IOExceptionHandler(@NotNull IOException ioException) {
        return new ResponseEntity<>(new ExceptionResponse(ioException.getMessage(), System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = InvalidFileTypeException.class)
    private @NotNull ResponseEntity<ExceptionResponse> invalidFileTypeExceptionHandler(@NotNull InvalidFileTypeException invalidFileTypeException) {
        return new ResponseEntity<>(new ExceptionResponse(invalidFileTypeException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = NullPointerException.class)
    private @NotNull ResponseEntity<ExceptionResponse> nullPointerExceptionHandler(@NotNull NullPointerException nullPointerException) {
        return new ResponseEntity<>(new ExceptionResponse(nullPointerException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = FileSizeTooLargeException.class)
    private @NotNull ResponseEntity<ExceptionResponse> fileSizeTooLargeExceptionHandler(@NotNull FileSizeTooLargeException fileSizeTooLargeException) {
        return new ResponseEntity<>(new ExceptionResponse(fileSizeTooLargeException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(value = "/{id}")
    public List<PostDTOO> viewByUser(@PathVariable(name = "id") int id) {
        return postService.findByUserId(id);
    }

    @Contract("_ -> new")
    @ExceptionHandler
    private @NotNull ResponseEntity<ExceptionResponse> userNotFoundExceptionHandler(@NotNull UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(new ExceptionResponse(userNotFoundException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    private void authenticationCheck(@NotNull HttpServletRequest httpServletRequest) {
        UserNotAuthenticatedException userNotAuthenticatedException = (UserNotAuthenticatedException) httpServletRequest.getAttribute("exception");

        if (userNotAuthenticatedException != null)
            throw userNotAuthenticatedException;
    }
}