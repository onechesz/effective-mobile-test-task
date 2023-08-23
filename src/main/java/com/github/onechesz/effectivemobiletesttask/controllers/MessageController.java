package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.dtos.message.MessageDTOI;
import com.github.onechesz.effectivemobiletesttask.secutiry.UserDetails;
import com.github.onechesz.effectivemobiletesttask.services.MessageService;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.MessageSendException;
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

import static com.github.onechesz.effectivemobiletesttask.controllers.AuthController.authenticationCheck;

@RestController
@RequestMapping(path = "/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(path = "/send")
    public ResponseEntity<HttpStatus> performSending(HttpServletRequest httpServletRequest, @RequestBody @Valid MessageDTOI messageDTOI, @NotNull BindingResult bindingResult) {
        authenticationCheck(httpServletRequest);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessagesBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMessagesBuilder.append(fieldError.getField()).append(" — ").append(fieldError.getDefaultMessage()).append(", ");

            errorMessagesBuilder.setLength(errorMessagesBuilder.length() - 2);

            throw new MessageSendException(errorMessagesBuilder.toString());
        }

        messageService.send(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), messageDTOI);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = MessageSendException.class)
    private @NotNull ResponseEntity<ExceptionResponse> messageSendExceptionHandler(@NotNull MessageSendException messageSendException) {
        return new ResponseEntity<>(new ExceptionResponse(messageSendException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }
}
