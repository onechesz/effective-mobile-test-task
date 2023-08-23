package com.github.onechesz.effectivemobiletesttask.entities;

import com.github.onechesz.effectivemobiletesttask.utils.embeddable.FollowId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "follow")
public class FollowEntity {
    @EmbeddedId
    private FollowId followId;

    public FollowEntity() {

    }

    public FollowEntity(FollowId followId) {
        this.followId = followId;
    }

    public FollowId getFollowId() {
        return followId;
    }

    public void setFollowId(FollowId followId) {
        this.followId = followId;
    }
}
