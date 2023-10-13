package com.example.socialplatform.repository;

import com.example.socialplatform.schema.Post;
import com.example.socialplatform.schema.User;
import com.example.socialplatform.schema.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
