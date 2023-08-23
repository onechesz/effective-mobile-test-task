package com.github.onechesz.effectivemobiletesttask.repostitories;

import com.github.onechesz.effectivemobiletesttask.entities.MessageEntity;
import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    @Query(value = "FROM MessageEntity WHERE (userEntity = :userEntity AND targetEntity = :targetEntity) OR (userEntity = :targetEntity AND targetEntity = :userEntity)")
    List<MessageEntity> findAllByUserEntityAndTargetEntity(UserEntity userEntity, UserEntity targetEntity);
}
