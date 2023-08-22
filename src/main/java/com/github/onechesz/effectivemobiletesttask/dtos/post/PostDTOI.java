package com.github.onechesz.effectivemobiletesttask.dtos.post;

import com.github.onechesz.effectivemobiletesttask.entities.PictureEntity;
import com.github.onechesz.effectivemobiletesttask.entities.PostEntity;
import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import com.github.onechesz.effectivemobiletesttask.services.PictureService;
import com.github.onechesz.effectivemobiletesttask.services.PostService;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

public class PostDTOI {
    public final static String POST_IMAGES_PATH = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "images";

    @jakarta.validation.constraints.NotNull(message = "не должно отсутствовать")
    @Size(min = 4, max = 255, message = "должно быть длиной от 4-х до 255-ти символов")
    private String title;

    private String text;

    private List<MultipartFile> pictureList;

    public PostDTOI() {

    }

    public PostDTOI(String title, String text, List<MultipartFile> pictureList) {
        this.title = title;
        this.text = text;
        this.pictureList = pictureList;
    }

    @Contract("_, _ -> new")
    public static @NotNull PostEntity convertToPostEntity(@NotNull PostDTOI postDTOI, UserEntity userEntity) {
        return new PostEntity(postDTOI.title, postDTOI.text, LocalDateTime.now(), userEntity, postDTOI.pictureList.stream().map(multipartFile -> {
            String newFileName = PictureService.generateRandomName() + PostService.getFileExtensionAndCheckIt(multipartFile);

            return new PictureEntity(newFileName, Paths.get(POST_IMAGES_PATH, newFileName).toString(), multipartFile.getContentType(), multipartFile.getSize());
        }).toList());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MultipartFile> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<MultipartFile> pictureList) {
        this.pictureList = pictureList;
    }
}
