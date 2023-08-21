package com.github.onechesz.effectivemobiletesttask.dtos;

import jakarta.validation.constraints.NotNull;

public class AuthenticationDTO {
    @NotNull(message = "не должно отсутствовать")
    private String name;

    @NotNull(message = "не должен отсутствовать")
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
