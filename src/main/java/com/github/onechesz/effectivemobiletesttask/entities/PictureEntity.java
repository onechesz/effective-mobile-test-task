package com.github.onechesz.effectivemobiletesttask.entities;

import com.github.onechesz.effectivemobiletesttask.dtos.picture.PictureDTOO;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "picture")
public class PictureEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "path", unique = true, nullable = false)
    private String path;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "size", nullable = false)
    private long size;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity postEntity;

    public PictureEntity() {

    }

    public PictureEntity(int id, String name, String type, long size) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public PictureEntity(String name, String path, String type, long size) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.size = size;
    }

    public PictureEntity(String name, String path, String type, long size, PostEntity postEntity) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.size = size;
        this.postEntity = postEntity;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull PictureDTOO convertToPictureDTOO(@NotNull PictureEntity pictureEntity) {
        return new PictureDTOO(pictureEntity.name, "http://localhost:8080/pictures/" + pictureEntity.id, pictureEntity.type, pictureEntity.size);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public PostEntity getPostEntity() {
        return postEntity;
    }

    public void setPostEntity(PostEntity postEntity) {
        this.postEntity = postEntity;
    }
}
