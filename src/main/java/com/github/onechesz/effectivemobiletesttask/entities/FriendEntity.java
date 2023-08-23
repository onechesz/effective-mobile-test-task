package com.github.onechesz.effectivemobiletesttask.entities;

import com.github.onechesz.effectivemobiletesttask.utils.embeddable.FriendId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "friend")
public class FriendEntity {
    @EmbeddedId
    private FriendId friendId;

    public FriendEntity() {

    }

    public FriendEntity(FriendId friendId) {
        this.friendId = friendId;
    }

    public FriendId getFriendId() {
        return friendId;
    }

    public void setFriendId(FriendId friendId) {
        this.friendId = friendId;
    }
}
