package com.github.onechesz.effectivemobiletesttask.utils.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FollowId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "followed_id")
    private int followedId;

    public FollowId() {

    }

    public FollowId(int userId, int followedId) {
        this.userId = userId;
        this.followedId = followedId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowedId() {
        return followedId;
    }

    public void setFollowedId(int followerId) {
        this.followedId = followerId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
