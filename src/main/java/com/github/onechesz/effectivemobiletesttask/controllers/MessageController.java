package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.dtos.message.MessageDTOI;
import com.github.onechesz.effectivemobiletesttask.dtos.message.MessageDTOO;
import com.github.onechesz.effectivemobiletesttask.secutiry.UserDetails;
import com.github.onechesz.effectivemobiletesttask.services.MessageService;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.MessageGetException;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.MessageSendException;
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

import java.util.List;

import static com.github.onechesz.effectivemobiletesttask.controllers.AuthController.authenticationCheck;

@RestController
@RequestMapping(path = "/messages")
@Tag(name = "Messages", description = "Сообщения")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(path = "/send")
    @Operation(summary = "Отправка сообщения пользователю")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Успешная отправка"),
            @ApiResponse(responseCode = "403", description = "Отсутствие авторизации"),
            @ApiResponse(responseCode = "406", description = "Ошибка при отправке")
    })
    public ResponseEntity<HttpStatus> performSending(HttpServletRequest httpServletRequest, @RequestBody @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Детали сообщения", required = true, content = @Content(schema = @Schema(implementation = MessageDTOI.class))) MessageDTOI messageDTOI, @NotNull BindingResult bindingResult) {
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

    @GetMapping(path = "/{id}")
    @Operation(summary = "Получение переписки с пользователем")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Переписка"),
            @ApiResponse(responseCode = "403", description = "Отсутствие авторизации"),
            @ApiResponse(responseCode = "406", description = "Ошибка при получении")
    })
    public List<MessageDTOO> viewAllWithUser(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);

        return messageService.findAllWithUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity(), id);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = MessageGetException.class)
    private @NotNull ResponseEntity<ExceptionResponse> messageGetExceptionHandler(@NotNull MessageGetException messageGetException) {
        return new ResponseEntity<>(new ExceptionResponse(messageGetException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }
}
