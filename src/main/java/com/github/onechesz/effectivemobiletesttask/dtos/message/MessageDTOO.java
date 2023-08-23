package com.github.onechesz.effectivemobiletesttask.dtos.message;

import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;

import java.time.LocalDateTime;

public class MessageDTOO {
    private UserEntity sender;

    private UserEntity target;

    private String text;

    private LocalDateTime sendTime;

    public MessageDTOO() {

    }

    public MessageDTOO(UserEntity sender, UserEntity target, String text, LocalDateTime sendTime) {
        this.sender = sender;
        this.target = target;
        this.text = text;
        this.sendTime = sendTime;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getTarget() {
        return target;
    }

    public void setTarget(UserEntity target) {
        this.target = target;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
