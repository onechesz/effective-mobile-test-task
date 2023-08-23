package com.github.onechesz.effectivemobiletesttask.dtos.message;

import com.github.onechesz.effectivemobiletesttask.entities.MessageEntity;
import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.jetbrains.annotations.Contract;

import java.time.LocalDateTime;

public class MessageDTOI {
    @Positive(message = "должен быть положительным числом")
    private int targetId;

    @NotNull(message = "не может отсутствовать")
    @NotBlank(message = "не может быть пустым")
    private String text;

    public MessageDTOI() {

    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @org.jetbrains.annotations.NotNull MessageEntity convertToMessageEntity(UserEntity userEntity, UserEntity targetEntity, @org.jetbrains.annotations.NotNull MessageDTOI messageDTOI, LocalDateTime sendTime) {
        return new MessageEntity(userEntity, targetEntity, messageDTOI.text, sendTime);
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
