package com.github.onechesz.effectivemobiletesttask.entities;

import com.github.onechesz.effectivemobiletesttask.dtos.post.PostDTOO;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
public class PostEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PictureEntity> pictureEntityList;

    public PostEntity() {

    }

    public PostEntity(String title, String text, LocalDateTime created, UserEntity userEntity, List<PictureEntity> pictureEntityList) {
        this.title = title;
        this.text = text;
        this.created = created;
        this.userEntity = userEntity;
        this.pictureEntityList = pictureEntityList;
    }

    @Contract("_ -> new")
    public static @NotNull PostDTOO convertToPostDTOO(@NotNull PostEntity postEntity) {
        return new PostDTOO(postEntity.title, postEntity.text, postEntity.getPictureEntityList().stream().map(PictureEntity::convertToPictureDTOO).toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<PictureEntity> getPictureEntityList() {
        return pictureEntityList;
    }

    public void setPictureEntityList(List<PictureEntity> pictureEntityList) {
        this.pictureEntityList = pictureEntityList;
    }
}
