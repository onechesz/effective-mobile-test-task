package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.secutiry.UserDetails;
import com.github.onechesz.effectivemobiletesttask.services.FriendService;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.FriendRemoveException;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.FriendRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.github.onechesz.effectivemobiletesttask.controllers.AuthController.authenticationCheck;

@RestController
@RequestMapping(path = "/friends")
@Tag(name = "Friends", description = "Друзья")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping(path = "/add/{id}")
    @Operation(summary = "Отправка запроса на дружбу")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Успешная отправка"),
            @ApiResponse(responseCode = "403", description = "Отсутствие авторизации"),
            @ApiResponse(responseCode = "406", description = "Ошибка при отправке")
    })
    public ResponseEntity<HttpStatus> performAdding(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);

        friendService.add(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), id);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/refuse/{id}")
    @Operation(summary = "Отмена исходящего запроса на дружбу")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Успешная отмена"),
            @ApiResponse(responseCode = "403", description = "Отсутствие авторизации"),
            @ApiResponse(responseCode = "406", description = "Ошибка при отмене")
    })
    public ResponseEntity<HttpStatus> performRefusing(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);

        friendService.refuse(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), id);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/accept/{id}")
    @Operation(summary = "Принятие входящего запроса на дружбу")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Успешное принятие"),
            @ApiResponse(responseCode = "403", description = "Отсутствие авторизации"),
            @ApiResponse(responseCode = "406", description = "Ошибка при принятии")
    })
    public ResponseEntity<HttpStatus> performAccepting(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);

        friendService.accept(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), id);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/reject/{id}")
    @Operation(summary = "Отклонение входящего запроса на дружбу")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Успешное отклонение"),
            @ApiResponse(responseCode = "403", description = "Отсутствие авторизации"),
            @ApiResponse(responseCode = "406", description = "Ошибка при отклонении")
    })
    public ResponseEntity<HttpStatus> performRejection(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);

        friendService.reject(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), id);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = FriendRequestException.class)
    private @NotNull ResponseEntity<ExceptionResponse> friendRequestExceptionHandler(@NotNull FriendRequestException friendRequestException) {
        return new ResponseEntity<>(new ExceptionResponse(friendRequestException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(path = "/remove/{id}")
    @Operation(summary = "Удаление пользователя из друзей")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Успешное удаление"),
            @ApiResponse(responseCode = "403", description = "Отсутствие авторизации"),
            @ApiResponse(responseCode = "406", description = "Ошибка при удалении")
    })
    public ResponseEntity<HttpStatus> performRemoving(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);

        friendService.remove(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), id);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = FriendRemoveException.class)
    private @NotNull ResponseEntity<ExceptionResponse> friendRemoveExceptionHandler(@NotNull FriendRequestException friendRequestException) {
        return new ResponseEntity<>(new ExceptionResponse(friendRequestException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }
}
