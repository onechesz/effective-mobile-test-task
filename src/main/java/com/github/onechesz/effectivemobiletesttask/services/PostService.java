package com.github.onechesz.effectivemobiletesttask.services;

import com.github.onechesz.effectivemobiletesttask.dtos.post.PostDTOI;
import com.github.onechesz.effectivemobiletesttask.dtos.post.PostDTOO;
import com.github.onechesz.effectivemobiletesttask.entities.PictureEntity;
import com.github.onechesz.effectivemobiletesttask.entities.PostEntity;
import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import com.github.onechesz.effectivemobiletesttask.repostitories.PictureRepository;
import com.github.onechesz.effectivemobiletesttask.repostitories.PostRepository;
import com.github.onechesz.effectivemobiletesttask.repostitories.UserRepository;
import com.github.onechesz.effectivemobiletesttask.utils.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.onechesz.effectivemobiletesttask.dtos.post.PostDTOI.POST_IMAGES_PATH;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, PictureRepository pictureRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
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

    public void create(@NotNull PostDTOI postDTOI, UserEntity userEntity) {
        List<MultipartFile> multipartFileList = postDTOI.getPictureList();

        if (multipartFileList == null) {
            multipartFileList = Collections.emptyList();
            postDTOI.setPictureList(Collections.emptyList());
        }

        PostEntity postEntity = PostDTOI.convertToPostEntity(postDTOI, userEntity);
        List<PictureEntity> pictureEntityList = postEntity.getPictureEntityList();

        for (PictureEntity pictureEntity : pictureEntityList)
            pictureEntity.setPostEntity(postEntity);

        saveAndCreatePictures(postEntity, pictureEntityList, multipartFileList);
    }

    @Transactional(readOnly = true)
    public List<PostDTOO> findByUserId(int userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            return postRepository.findByUserEntity(userEntity).stream().map(PostEntity::convertToPostDTOO).toList();
        } else
            throw new UserNotFoundException("пользователь с таким идентификатором не найден");
    }

    public void update(PostDTOI postDTOI, UserEntity userEntity, int id) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(id);

        if (postEntityOptional.isPresent()) {
            PostEntity postEntity = postEntityOptional.get();

            if (postEntity.getUserEntity().getId() == userEntity.getId()) {
                postEntity.setTitle(postDTOI.getTitle());
                postEntity.setText(postDTOI.getText());
                pictureRepository.deleteAllByPostEntity(postEntity);
                deletePicturesFiles(postEntity);

                List<PictureEntity> pictureEntityList = new ArrayList<>();
                List<MultipartFile> multipartFileList = postDTOI.getPictureList();

                if (multipartFileList != null)
                    pictureEntityList = postDTOI.getPictureList().stream().map(multipartFile -> {
                        String newFileName = PictureService.generateRandomName() + getFileExtensionAndCheckIt(multipartFile);

                        return new PictureEntity(newFileName, Paths.get(POST_IMAGES_PATH, newFileName).toString(), multipartFile.getContentType(), multipartFile.getSize(), postEntity);
                    }).collect(Collectors.toList());

                postEntity.setPictureEntityList(pictureEntityList);
                saveAndCreatePictures(postEntity, pictureEntityList, multipartFileList);
            } else
                throw new PostNotUpdatedException("автор поста не совпадает с тем, кто пытается его редактировать");
        } else
            throw new PostNotUpdatedException("пост с таким идентификатором не найден");
    }

    private void saveAndCreatePictures(PostEntity postEntity, @NotNull List<PictureEntity> pictureEntityList, List<MultipartFile> multipartFileList) {
        postRepository.save(postEntity);

        for (int i = 0; i < pictureEntityList.size(); i++) {
            try {
                multipartFileList.get(i).transferTo(new File(POST_IMAGES_PATH + File.separator + pictureEntityList.get(i).getName()));
            } catch (IOException ioException) {
                throw new FileNotCreatedException("внутренняя ошибка при сохранении изображения");
            }
        }
    }

    public void delete(int id, UserEntity userEntity) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(id);

        if (postEntityOptional.isPresent()) {
            PostEntity postEntity = postEntityOptional.get();

            if (postEntity.getUserEntity().getId() == userEntity.getId()) {
                postRepository.delete(postEntity);
                deletePicturesFiles(postEntity);
            } else
                throw new PostNotDeletedException("автор поста не совпадает с тем, кто пытается его удалить");
        } else
            throw new PostNotDeletedException("пост с таким идентификатором не найден");
    }

    private void deletePicturesFiles(@NotNull PostEntity postEntity) {
        for (PictureEntity pictureEntity : postEntity.getPictureEntityList())
            try {
                Files.delete(Path.of(pictureEntity.getPath()));
            } catch (NoSuchFileException noSuchFileException) {
                throw new FileNotDeletedException("внутренняя ошибка удаления файла: файл по такому пути не найден");
            } catch (DirectoryNotEmptyException directoryNotEmptyException) {
                throw new FileNotDeletedException("внутренняя ошибка удаления файла: директория не пустая");
            } catch (IOException ioException) {
                throw new FileNotDeletedException("внутренняя ошибка удаления файла");
            }
    }
}
