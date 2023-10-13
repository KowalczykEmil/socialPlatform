package com.example.socialplatform.controller;

import com.example.socialplatform.datatransferobject.CommentsDataTransferObject;
import com.example.socialplatform.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/komentarze")
@AllArgsConstructor
public class CommentsCntrl {
    private final CommentService commentService;

    @GetMapping("/dlaWpisu/{postId}")
    public ResponseEntity<List<CommentsDataTransferObject>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK).body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/dlaUzytkownika/{userName}")
    public ResponseEntity<List<CommentsDataTransferObject>> getAllCommentsForUser(@PathVariable String userName){
        return ResponseEntity.status(OK).body(commentService.getAllCommentsForUser(userName));
    }

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDataTransferObject commentsDataTransferObject) {
        commentService.save(commentsDataTransferObject);

        return new ResponseEntity<>(CREATED);
    }
}
