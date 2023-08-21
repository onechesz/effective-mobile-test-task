package com.github.onechesz.effectivemobiletesttask.repostitories;

import com.github.onechesz.effectivemobiletesttask.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

}
