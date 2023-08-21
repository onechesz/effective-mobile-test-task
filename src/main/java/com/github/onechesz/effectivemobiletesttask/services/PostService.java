package com.github.onechesz.effectivemobiletesttask.services;

import com.github.onechesz.effectivemobiletesttask.dtos.PostDTO;
import com.github.onechesz.effectivemobiletesttask.entities.PictureEntity;
import com.github.onechesz.effectivemobiletesttask.entities.PostEntity;
import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import com.github.onechesz.effectivemobiletesttask.repostitories.PostRepository;
import com.github.onechesz.effectivemobiletesttask.utils.FileSizeTooLargeException;
import com.github.onechesz.effectivemobiletesttask.utils.InvalidFileTypeException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public static @NotNull String generateRandomName() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder(60);

        for (int i = 0; i < 60; i++)
            stringBuilder.append(characters.charAt(ThreadLocalRandom.current().nextInt(characters.length())));

        return stringBuilder.toString();
    }

    public static @NotNull String getFileExtensionAndCheckIt(@NotNull MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        long size = multipartFile.getSize();

        if (size > 1e+7)
            throw new FileSizeTooLargeException("слишком большой размер файла (максимум - 10мб)");

        if (contentType != null) {
            switch (contentType) {
                case "image/jpeg" -> {
                    return ".jpg";
                }
                case "image/png" -> {
                    return ".png";
                }
                default -> throw new InvalidFileTypeException("принимаются только изображения форматов jpg, png");
            }
        } else
            throw new NullPointerException("изображение не может быть null");
    }

    public void create(@NotNull PostDTO postDTO, UserEntity userEntity) throws IOException {
        List<MultipartFile> multipartFileList = postDTO.getPictureList();

        if (multipartFileList == null) {
            multipartFileList = Collections.emptyList();
            postDTO.setPictureList(Collections.emptyList());
        }

        PostEntity postEntity = PostDTO.convertToPostEntity(postDTO, userEntity);
        List<PictureEntity> pictureEntityList = postEntity.getPictureEntityList();

        for (PictureEntity pictureEntity : pictureEntityList)
            pictureEntity.setPostEntity(postEntity);

        postRepository.save(postEntity);

        for (int i = 0; i < pictureEntityList.size(); i++)
            multipartFileList.get(i).transferTo(new File(PostDTO.POST_IMAGES_PATH + File.separator + pictureEntityList.get(i).getName()));
    }
}
