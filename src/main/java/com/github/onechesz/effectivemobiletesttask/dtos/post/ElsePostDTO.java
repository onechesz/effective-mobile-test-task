package com.github.onechesz.effectivemobiletesttask.dtos.post;

import com.github.onechesz.effectivemobiletesttask.dtos.picture.PictureDTOO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ElsePostDTO {
    private int postId;

    private String title;

    private String text;

    private String author;

    private LocalDateTime created;

    private List<PictureDTOO> pictureDTOOList;

    public ElsePostDTO() {

    }

    public ElsePostDTO(int postId, String title, String text, String author, LocalDateTime created) {
        this.postId = postId;
        this.title = title;
        this.text = text;
        this.author = author;
        this.created = created;
        this.pictureDTOOList = new ArrayList<>();
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<PictureDTOO> getPictureDTOOList() {
        return pictureDTOOList;
    }

    public void setPictureDTOOList(List<PictureDTOO> pictureDTOOList) {
        this.pictureDTOOList = pictureDTOOList;
    }
}
