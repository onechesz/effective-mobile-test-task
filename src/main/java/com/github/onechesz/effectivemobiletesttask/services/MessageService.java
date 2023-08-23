package com.github.onechesz.effectivemobiletesttask.services;

import com.github.onechesz.effectivemobiletesttask.dtos.message.MessageDTOI;
import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import com.github.onechesz.effectivemobiletesttask.repostitories.FriendRepository;
import com.github.onechesz.effectivemobiletesttask.repostitories.MessageRepository;
import com.github.onechesz.effectivemobiletesttask.repostitories.UserRepository;
import com.github.onechesz.effectivemobiletesttask.utils.embeddable.FriendId;
import com.github.onechesz.effectivemobiletesttask.utils.exceptions.MessageSendException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class MessageService {
    private final MessageRepository messageRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, FriendRepository friendRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public void send(@NotNull UserEntity userEntity, @NotNull MessageDTOI messageDTOI) {
        int userId = userEntity.getId();
        int targetId = messageDTOI.getTargetId();

        if (userId == targetId)
            throw new MessageSendException("вы не можете отправить сообщение самому себе");

        friendRepository.findById(new FriendId(userId, targetId)).ifPresentOrElse(friendEntity -> {
            userRepository.findById(targetId).ifPresentOrElse(targetEntity -> messageRepository.save(MessageDTOI.convertToMessageEntity(userEntity, targetEntity, messageDTOI, LocalDateTime.now())), () -> {
                throw new MessageSendException("адресат с таким идентификатором не найден");
            });
        }, () -> {
            throw new MessageSendException("вы не являетесь друзьями с адресатом");
        });
    }
}
