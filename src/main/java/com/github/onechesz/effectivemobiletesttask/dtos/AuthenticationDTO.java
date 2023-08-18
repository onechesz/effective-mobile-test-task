package com.github.onechesz.effectivemobiletesttask.dtos;

public class AuthenticationDTO {
    private String name;

    private String password;

    public AuthenticationDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
