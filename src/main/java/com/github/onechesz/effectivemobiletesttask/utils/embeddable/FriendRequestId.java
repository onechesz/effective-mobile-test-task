package com.github.onechesz.effectivemobiletesttask.utils.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FriendRequestId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "target_id")
    private int targetId;

    public FriendRequestId() {

    }

    public FriendRequestId(int userId, int targetId) {
        this.userId = userId;
        this.targetId = targetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
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
