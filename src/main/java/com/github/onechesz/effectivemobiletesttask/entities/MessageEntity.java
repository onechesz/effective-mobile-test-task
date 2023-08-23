package com.github.onechesz.effectivemobiletesttask.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class MessageEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "target_id", nullable = false)
    private UserEntity targetEntity;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "send_time")
    private LocalDateTime sendTime;

    public MessageEntity() {

    }

    public MessageEntity(UserEntity userEntity, UserEntity targetEntity, String text, LocalDateTime sendTime) {
        this.userEntity = userEntity;
        this.targetEntity = targetEntity;
        this.text = text;
        this.sendTime = sendTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(UserEntity targetEntity) {
        this.targetEntity = targetEntity;
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
