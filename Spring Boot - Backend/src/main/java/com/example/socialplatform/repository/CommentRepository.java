package com.example.socialplatform.repository;

import com.example.socialplatform.schema.Comment;
import com.example.socialplatform.schema.Post;
import com.example.socialplatform.schema.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
