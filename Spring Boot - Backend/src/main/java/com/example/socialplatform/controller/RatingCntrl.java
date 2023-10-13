package com.example.socialplatform.controller;

import com.example.socialplatform.datatransferobject.RatingDataTransferObject;
import com.example.socialplatform.exception.SocialPlatformException;
import com.example.socialplatform.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oceny/")
@AllArgsConstructor
public class RatingCntrl {

    private final RatingService ratingService;

    @PostMapping("post/{postId}/upvote")
    public ResponseEntity<Void> upvote(@PathVariable Long postId) {
        ratingService.upvote(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("post/{postId}/downvote")
    public ResponseEntity<Void> downvote(@PathVariable Long postId) {
        ratingService.downvote(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({SocialPlatformException.class})
    ResponseEntity<String> handleException(SocialPlatformException socialPlatformException) {
        return ResponseEntity.badRequest().body(socialPlatformException.getMessage());
    }
}
