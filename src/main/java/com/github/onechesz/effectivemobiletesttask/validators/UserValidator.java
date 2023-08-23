package com.github.onechesz.effectivemobiletesttask.validators;

import com.github.onechesz.effectivemobiletesttask.dtos.user.UserDTO;
import com.github.onechesz.effectivemobiletesttask.repostitories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        if (userRepository.findByName(userDTO.getName()).isPresent())
            errors.rejectValue("name", "", "пользователь с таким именем уже зарегистрирован");

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent())
            errors.rejectValue("email", "", "пользователь с такой почтой уже зарегистрирован");
    }
}
