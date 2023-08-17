package com.github.onechesz.effectivemobiletesttask.dtos;

import jakarta.validation.constraints.Size;

public class AuthenticationDTO {
    @Size(min = 4, max = 32, message = "должно быть длиной от 4-х до 32-х символов")
    private String name;

    @Size(min = 7, max = 32, message = "должен быть длиной от 7-ми до 32-х символов")
    private String password;
}
