package com.github.onechesz.effectivemobiletesttask.services;

import com.github.onechesz.effectivemobiletesttask.dtos.post.PostDTOI;
import com.github.onechesz.effectivemobiletesttask.dtos.post.PostDTOO;
import com.github.onechesz.effectivemobiletesttask.entities.PictureEntity;
import com.github.onechesz.effectivemobiletesttask.entities.PostEntity;
import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import com.github.onechesz.effectivemobiletesttask.repostitories.PostRepository;
import com.github.onechesz.effectivemobiletesttask.repostitories.UserRepository;
import com.github.onechesz.effectivemobiletesttask.utils.FileSizeTooLargeException;
import com.github.onechesz.effectivemobiletesttask.utils.InvalidFileTypeException;
import com.github.onechesz.effectivemobiletesttask.utils.UserNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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

    public void create(@NotNull PostDTOI postDTOI, UserEntity userEntity) throws IOException {
        List<MultipartFile> multipartFileList = postDTOI.getPictureList();

        if (multipartFileList == null) {
            multipartFileList = Collections.emptyList();
            postDTOI.setPictureList(Collections.emptyList());
        }

        PostEntity postEntity = PostDTOI.convertToPostEntity(postDTOI, userEntity);
        List<PictureEntity> pictureEntityList = postEntity.getPictureEntityList();

        for (PictureEntity pictureEntity : pictureEntityList)
            pictureEntity.setPostEntity(postEntity);

        postRepository.save(postEntity);

        for (int i = 0; i < pictureEntityList.size(); i++)
            multipartFileList.get(i).transferTo(new File(PostDTOI.POST_IMAGES_PATH + File.separator + pictureEntityList.get(i).getName()));
    }

    public List<PostDTOO> findByUserId(int userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            return postRepository.findByUserEntity(userEntity).stream().map(PostEntity::convertToPostDTOO).toList();
        } else
            throw new UserNotFoundException("пользователь с таким идентификатором не найден");
    }
}
