package com.github.onechesz.effectivemobiletesttask.repostitories;

import com.github.onechesz.effectivemobiletesttask.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

}
