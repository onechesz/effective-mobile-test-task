package com.github.onechesz.effectivemobiletesttask.services;

import com.github.onechesz.effectivemobiletesttask.entities.PictureEntity;
import com.github.onechesz.effectivemobiletesttask.repostitories.PictureRepository;
import com.github.onechesz.effectivemobiletesttask.utils.PictureNotLoadedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Transactional
public class PictureService {
    private final PictureRepository pictureRepository;

    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public Optional<PictureEntity> findById(int id) {
        return pictureRepository.findById(id);
    }

    public Resource getResource(@NotNull PictureEntity pictureEntity) {
        try {
            Path filePath = Paths.get(pictureEntity.getPath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable())
                return resource;
            else
                throw new PictureNotLoadedException("ошибка при загрузке изображения");
        } catch (MalformedURLException e) {
            throw new PictureNotLoadedException("некорректный путь до изображения");
        }
    }
}
