package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.secutiry.UserDetails;
import com.github.onechesz.effectivemobiletesttask.services.FriendService;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.FriendRequestException;
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
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping(path = "/add/{id}")
    public ResponseEntity<HttpStatus> performAdding(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);

        friendService.add(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), id);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/refuse/{id}")
    public ResponseEntity<HttpStatus> performRefusing(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);

        friendService.refuse(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), id);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/accept/{id}")
    public ResponseEntity<HttpStatus> performAccepting(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);

        friendService.accept(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), id);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/reject/{id}")
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
}
