package com.github.onechesz.effectivemobiletesttask.dtos.user;

import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

public class UserDTO {
    @jakarta.validation.constraints.NotNull(message = "не должно отсутствовать")
    @Size(min = 4, max = 32, message = "должно быть длиной от 4-х до 32-х символов")
    private String name;

    @jakarta.validation.constraints.NotNull(message = "не должен отсутствовать")
    @Email(message = "должен соответствовать структуре e-mail")
    private String email;

    @jakarta.validation.constraints.NotNull(message = "не должен отсутствовать")
    @Size(min = 7, max = 32, message = "должен быть длиной от 7-ми до 32-х символов")
    private String password;

    public UserDTO() {

    }

    public static @NotNull UserEntity convertToUserEntity(String role, @NotNull UserDTO userDTO) {
        return new UserEntity(role, userDTO.name, userDTO.email, userDTO.password.toCharArray());
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
