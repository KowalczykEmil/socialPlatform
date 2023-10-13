package com.example.socialplatform.controller;

import com.example.socialplatform.datatransferobject.PostRequest;
import com.example.socialplatform.datatransferobject.PostResponse;
import com.example.socialplatform.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/wpisy/")
@AllArgsConstructor
public class PostCntrl {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("dlaTagu/{id}")
    public ResponseEntity<List<PostResponse>> getPostsByTag(Long id) {
        return status(HttpStatus.OK).body(postService.getPostsByTag(id));
    }

    @GetMapping("dlaUzytkownika/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("tag")
    public ResponseEntity<List<PostResponse>> getPostsByTagName(@RequestParam(name = "tagName") String name) {
        return status(HttpStatus.OK).body(postService.getPostsByTag(name));
    }

}
