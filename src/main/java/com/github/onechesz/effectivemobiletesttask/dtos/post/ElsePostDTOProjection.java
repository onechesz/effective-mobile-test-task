package com.github.onechesz.effectivemobiletesttask.dtos.post;

import java.time.LocalDateTime;

public interface ElsePostDTOProjection {
    Integer getPostId();

    String getTitle();

    String getText();

    String getAuthor();

    LocalDateTime getCreated();

    Integer getId();

    String getName();

    String getType();

    Long getSize();
}
