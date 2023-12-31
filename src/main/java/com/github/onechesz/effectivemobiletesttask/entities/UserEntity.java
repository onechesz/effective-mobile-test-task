package com.github.onechesz.effectivemobiletesttask.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "\"user\"")
public class UserEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    @JsonIgnore
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private char[] password;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PostEntity> postEntityList;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MessageEntity> sentMessagesList;

    @OneToMany(mappedBy = "targetEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MessageEntity> receivedMessagesList;

    public UserEntity() {

    }

    public UserEntity(String role, String name, String email, char[] password) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public List<PostEntity> getPostEntityList() {
        return postEntityList;
    }

    public void setPostEntityList(List<PostEntity> postEntityList) {
        this.postEntityList = postEntityList;
    }

    public List<MessageEntity> getSentMessagesList() {
        return sentMessagesList;
    }

    public void setSentMessagesList(List<MessageEntity> sentMessagesList) {
        this.sentMessagesList = sentMessagesList;
    }

    public List<MessageEntity> getReceivedMessagesList() {
        return receivedMessagesList;
    }

    public void setReceivedMessagesList(List<MessageEntity> receivedMessagesList) {
        this.receivedMessagesList = receivedMessagesList;
    }
}
