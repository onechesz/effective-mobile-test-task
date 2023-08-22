package com.github.onechesz.effectivemobiletesttask.dtos.post;

import com.github.onechesz.effectivemobiletesttask.dtos.picture.PictureDTOO;

import java.util.List;

public class PostDTOO {
    private String title;

    private String text;

    private List<PictureDTOO> pictureDTOOList;

    public PostDTOO() {

    }

    public PostDTOO(String title, String text, List<PictureDTOO> pictureDTOOList) {
        this.title = title;
        this.text = text;
        this.pictureDTOOList = pictureDTOOList;
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

    public List<PictureDTOO> getPictureDTOOList() {
        return pictureDTOOList;
    }

    public void setPictureDTOOList(List<PictureDTOO> pictureDTOOList) {
        this.pictureDTOOList = pictureDTOOList;
    }
}
