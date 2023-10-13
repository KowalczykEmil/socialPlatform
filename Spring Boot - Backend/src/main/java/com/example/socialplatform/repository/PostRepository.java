package com.example.socialplatform.repository;

import com.example.socialplatform.schema.Post;
import com.example.socialplatform.schema.Tag;
import com.example.socialplatform.schema.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTag(Tag tag);
    List<Post> findByUser(User user);
}
