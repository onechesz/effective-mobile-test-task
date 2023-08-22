package com.github.onechesz.effectivemobiletesttask.repostitories;

import com.github.onechesz.effectivemobiletesttask.entities.PostEntity;
import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByUserEntity(UserEntity userEntity);
}
