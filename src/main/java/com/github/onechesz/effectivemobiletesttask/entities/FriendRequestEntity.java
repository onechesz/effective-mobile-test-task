package com.github.onechesz.effectivemobiletesttask.entities;

import com.github.onechesz.effectivemobiletesttask.utils.embeddable.FriendRequestId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "friend_request")
public class FriendRequestEntity {
    @EmbeddedId
    private FriendRequestId friendRequestId;

    public FriendRequestEntity() {

    }

    public FriendRequestEntity(FriendRequestId friendRequestId) {
        this.friendRequestId = friendRequestId;
    }

    public FriendRequestId getFriendRequestId() {
        return friendRequestId;
    }

    public void setFriendRequestId(FriendRequestId friendRequestId) {
        this.friendRequestId = friendRequestId;
    }
}
