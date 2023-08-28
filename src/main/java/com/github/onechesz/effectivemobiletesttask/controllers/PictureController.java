package com.github.onechesz.effectivemobiletesttask.controllers;

import com.github.onechesz.effectivemobiletesttask.entities.PictureEntity;
import com.github.onechesz.effectivemobiletesttask.services.PictureService;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.PictureNotFoundException;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.PictureNotLoadedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/pictures")
@Tag(name = "Pictures", description = "Картинки")
public class PictureController {
    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Просмотр картинки")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Картинка"),
            @ApiResponse(responseCode = "404", description = "Ошибка при запросе"),
            @ApiResponse(responseCode = "406", description = "Ошибка при просмотре")
    })
    public ResponseEntity<Resource> viewPicture(@PathVariable(name = "id") int id) {
        Optional<PictureEntity> pictureEntityOptional = pictureService.findById(id);

        if (pictureEntityOptional.isPresent()) {
            PictureEntity pictureEntity = pictureEntityOptional.get();

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(pictureEntity.getType())).body(pictureService.getResource(pictureEntity));
        } else
            throw new PictureNotFoundException("изображение с таким идентификатором не найдено");
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = PictureNotFoundException.class)
    private @NotNull ResponseEntity<ExceptionResponse> pictureNotFoundExceptionHandler(@NotNull PictureNotFoundException pictureNotFoundException) {
        return new ResponseEntity<>(new ExceptionResponse(pictureNotFoundException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = PictureNotLoadedException.class)
    private @NotNull ResponseEntity<ExceptionResponse> pictureNotLoadedExceptionHandler(@NotNull PictureNotLoadedException pictureNotLoadedException) {
        return new ResponseEntity<>(new ExceptionResponse(pictureNotLoadedException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }
}
