package com.github.onechesz.effectivemobiletesttask.services;

import com.github.onechesz.effectivemobiletesttask.entities.FollowEntity;
import com.github.onechesz.effectivemobiletesttask.entities.FriendEntity;
import com.github.onechesz.effectivemobiletesttask.entities.FriendRequestEntity;
import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import com.github.onechesz.effectivemobiletesttask.repostitories.FollowRepository;
import com.github.onechesz.effectivemobiletesttask.repostitories.FriendRepository;
import com.github.onechesz.effectivemobiletesttask.repostitories.FriendRequestRepository;
import com.github.onechesz.effectivemobiletesttask.repostitories.UserRepository;
import com.github.onechesz.effectivemobiletesttask.utils.embeddable.FollowId;
import com.github.onechesz.effectivemobiletesttask.utils.embeddable.FriendId;
import com.github.onechesz.effectivemobiletesttask.utils.embeddable.FriendRequestId;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.FriendRequestException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;
    private final FollowRepository followRepository;

    public FriendService(UserRepository userRepository, FriendRequestRepository friendRequestRepository, FriendRepository friendRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.friendRepository = friendRepository;
        this.followRepository = followRepository;
    }

    public void add(@NotNull UserEntity userEntity, int id) {
        int userId = userEntity.getId();

        if (userId == id)
            throw new FriendRequestException("нельзя отправить запрос на дружбу самому себе");

        userRepository.findById(id).ifPresentOrElse(userEntity1 -> {
            friendRequestRepository.findById(new FriendRequestId(userId, id)).ifPresent(friendRequestEntity -> {
                throw new FriendRequestException("запрос на дружбу с этим пользователем уже есть");
            });

            friendRepository.findById(new FriendId(userId, id)).ifPresent(friendEntity -> {
                throw new FriendRequestException("вы уже дружите с этим пользователем");
            });

            friendRequestRepository.findById(new FriendRequestId(id, userId)).ifPresentOrElse(friendRequestEntity -> {
                friendRequestRepository.delete(friendRequestEntity);
                makeFriends(userId, id);
                followRepository.save(new FollowEntity(new FollowId(userId, id)));
            }, () -> {
                friendRequestRepository.save(new FriendRequestEntity(new FriendRequestId(userId, id)));
                followRepository.save(new FollowEntity(new FollowId(userId, id)));
            });
        }, () -> {
            throw new FriendRequestException("пользователя с таким идентификатором, который вы отправили для добавления по нему в друзья, не существует");
        });
    }

    public void refuse(@NotNull UserEntity userEntity, int id) {
        int userId = userEntity.getId();

        if (userId == id)
            throw new FriendRequestException("нельзя отказаться от подписки на самого себя");

        friendRequestRepository.findById(new FriendRequestId(userId, id)).ifPresentOrElse(friendRequestEntity -> {
            friendRequestRepository.delete(friendRequestEntity);
            followRepository.findById(new FollowId(userId, id)).ifPresent(followRepository::delete);
        }, () -> {
            throw new FriendRequestException("нет запроса на подписку на пользователя с таким идентификатором");
        });
    }

    private void makeFriends(int userId, int friendId) {
        friendRepository.save(new FriendEntity(new FriendId(userId, friendId)));
        friendRepository.save(new FriendEntity(new FriendId(friendId, userId)));
    }
}
