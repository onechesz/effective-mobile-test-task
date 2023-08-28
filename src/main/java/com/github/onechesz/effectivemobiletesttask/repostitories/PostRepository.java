package com.github.onechesz.effectivemobiletesttask.repostitories;

import com.github.onechesz.effectivemobiletesttask.dtos.post.ElsePostDTOProjection;
import com.github.onechesz.effectivemobiletesttask.entities.PostEntity;
import com.github.onechesz.effectivemobiletesttask.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByUserEntity(UserEntity userEntity);

    @Query(value =
            "SELECT " +
                    "p.id AS \"postId\", " +
                    "p.title AS \"title\", " +
                    "p.text AS \"text\", " +
                    "fr.name AS \"author\", " +
                    "p.created AS \"created\", " +
                    "pi.id AS \"id\", " +
                    "pi.name AS \"name\", " +
                    "pi.type AS \"type\", " +
                    "pi.size AS \"size\" " +
                    "FROM \"post\" p " +
                    "JOIN \"friend\" f ON p.user_id = f.friend_id " +
                    "JOIN \"user\" u ON u.id = f.user_id " +
                    "LEFT JOIN \"picture\" pi ON pi.post_id = p.id " +
                    "JOIN \"user\" fr ON f.friend_id = fr.id " +
                    "WHERE u.id = :userId " +
                    "ORDER BY p.created", nativeQuery = true)
    List<ElsePostDTOProjection> findAllByFriends(@Param(value = "userId") int userId);
}
