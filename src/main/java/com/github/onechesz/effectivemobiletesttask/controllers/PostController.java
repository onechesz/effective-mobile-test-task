package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.dtos.post.ElsePostDTO;
import com.github.onechesz.effectivemobiletesttask.dtos.post.PostDTOI;
import com.github.onechesz.effectivemobiletesttask.dtos.post.PostDTOO;
import com.github.onechesz.effectivemobiletesttask.secutiry.UserDetails;
import com.github.onechesz.effectivemobiletesttask.services.PostService;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.*;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.github.onechesz.effectivemobiletesttask.controllers.AuthController.authenticationCheck;

@RestController
@RequestMapping(path = "/posts")
@Tag(name = "Posts", description = "Посты")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(path = "/create")
    @Operation(summary = "Создание поста")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Успешное создание"),
            @ApiResponse(responseCode = "400", description = "Ошибка создания"),
            @ApiResponse(responseCode = "403", description = "Ошибка авторизации"),
            @ApiResponse(responseCode = "406", description = "Ошибка с файлом")
    })
    public ResponseEntity<HttpStatus> performCreation(HttpServletRequest httpServletRequest, @RequestBody @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Детали поста", required = true, content = @Content(schema = @Schema(implementation = PostDTOI.class))) PostDTOI postDTOI, @NotNull BindingResult bindingResult) throws IOException {
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
    @Operation(summary = "Просмотр постов пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Посты"),
            @ApiResponse(responseCode = "403", description = "Ошибка авторизации"),
            @ApiResponse(responseCode = "404", description = "Ошибка нахождения пользователя")
    })
    public List<PostDTOO> viewByUser(@PathVariable(name = "id") int id) {
        return postService.findByUserId(id);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = UserNotFoundException.class)
    private @NotNull ResponseEntity<ExceptionResponse> userNotFoundExceptionHandler(@NotNull UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(new ExceptionResponse(userNotFoundException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/{id}/update")
    @Operation(summary = "Обновление поста")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Успешное обновление"),
            @ApiResponse(responseCode = "403", description = "Ошибка авторизации"),
            @ApiResponse(responseCode = "406", description = "Ошибка обновления")
    })
    public ResponseEntity<HttpStatus> performUpdating(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id, @RequestBody @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Детали поста", required = true, content = @Content(schema = @Schema(implementation = PostDTOI.class))) PostDTOI postDTOI, @NotNull BindingResult bindingResult) {
        authenticationCheck(httpServletRequest);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessagesBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMessagesBuilder.append(fieldError.getField()).append(" — ").append(fieldError.getDefaultMessage()).append(", ");

            errorMessagesBuilder.setLength(errorMessagesBuilder.length() - 2);

            throw new PostNotUpdatedException(errorMessagesBuilder.toString());
        }

        postService.update(postDTOI, ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), id);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = PostNotUpdatedException.class)
    private @NotNull ResponseEntity<ExceptionResponse> postNotUpdatedExceptionHandler(@NotNull PostNotUpdatedException postNotUpdatedException) {
        return new ResponseEntity<>(new ExceptionResponse(postNotUpdatedException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Удаление поста")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Успешное удаление"),
            @ApiResponse(responseCode = "403", description = "Отсутствие авторизации"),
            @ApiResponse(responseCode = "406", description = "Ошибка удаления")
    })
    public ResponseEntity<HttpStatus> performDeleting(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);

        postService.delete(id, ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity());

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = PostNotDeletedException.class)
    private @NotNull ResponseEntity<ExceptionResponse> postNotDeletedExceptionHandler(@NotNull PostNotDeletedException postNotDeletedException) {
        return new ResponseEntity<>(new ExceptionResponse(postNotDeletedException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(path = "")
    @Operation(summary = "Просмотр ленты")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Посты"),
            @ApiResponse(responseCode = "403", description = "Отсутствие авторизации"),
            @ApiResponse(responseCode = "406", description = "Ошибка просмотра")
    })
    public List<ElsePostDTO> viewFeed(HttpServletRequest httpServletRequest, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "5") int size) {
        authenticationCheck(httpServletRequest);

        StringBuilder exceptionMessagesBuilder = new StringBuilder();

        if (page < 1)
            exceptionMessagesBuilder.append("page — должна быть положительным числом, ");

        if (size < 1) {
            exceptionMessagesBuilder.append("size — должен быть положительным числом");

            throw new PostsNotGetException(exceptionMessagesBuilder.toString());
        } else if (size > 20) {
            exceptionMessagesBuilder.append("size — максимальное количество постов на странице: 20");

            throw new PostsNotGetException(exceptionMessagesBuilder.toString());
        }

        return postService.findAllByFriends(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), page, size);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = PostsNotGetException.class)
    private @NotNull ResponseEntity<ExceptionResponse> postsNotGetExceptionHandler(@NotNull PostsNotGetException postsNotGetException) {
        return new ResponseEntity<>(new ExceptionResponse(postsNotGetException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }
}
