package com.github.onechesz.effectivemobiletesttask.services;

import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import com.github.onechesz.effectivemobiletesttask.repostitories.UserRepository;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.ExceptionResponse;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = userRepository.findByName(username);

        if (userEntityOptional.isEmpty())
            throw new UsernameNotFoundException("пользователь с таким именем не найден");

        return new com.github.onechesz.effectivemobiletesttask.secutiry.UserDetails(userEntityOptional.get());
    }

    @Contract("_ -> new")
    @ExceptionHandler
    private @NotNull ResponseEntity<ExceptionResponse> userNotFoundHandler(@NotNull UsernameNotFoundException usernameNotFoundException) {
        return new ResponseEntity<>(new ExceptionResponse(usernameNotFoundException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }
}
