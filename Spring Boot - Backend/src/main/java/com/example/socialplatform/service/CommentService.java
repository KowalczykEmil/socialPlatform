package com.example.socialplatform.service;

import com.example.socialplatform.datatransferobject.CommentsDataTransferObject;
import com.example.socialplatform.exception.PostNotFoundException;
import com.example.socialplatform.exception.SocialPlatformException;
import com.example.socialplatform.mapper.CommentMapper;
import com.example.socialplatform.repository.CommentRepository;
import com.example.socialplatform.repository.PostRepository;
import com.example.socialplatform.repository.UserRepository;
import com.example.socialplatform.schema.Comment;
import com.example.socialplatform.schema.NotificationEmail;
import com.example.socialplatform.schema.Post;
import com.example.socialplatform.schema.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailBuilder mailBuilder;
    private final MailService mailService;

    @Transactional
    public void save(CommentsDataTransferObject commentsDataTransferObject) {
        Post post = postRepository.findById(commentsDataTransferObject.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDataTransferObject.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDataTransferObject, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailBuilder.build(post.getUser().getUsername() + " Dodał komentarz przy Twoim poście. " + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Skometował Twój post", user.getEmail(), message));
    }
    @Transactional(readOnly = true)
    public List<CommentsDataTransferObject> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }
    @Transactional(readOnly = true)
    public List<CommentsDataTransferObject> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new SocialPlatformException("Comments contains unacceptable language");
        }
        return false;
    }
}

