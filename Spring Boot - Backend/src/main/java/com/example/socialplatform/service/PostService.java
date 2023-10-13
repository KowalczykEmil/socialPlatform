package com.example.socialplatform.service;

import com.example.socialplatform.datatransferobject.PostRequest;
import com.example.socialplatform.datatransferobject.PostResponse;
import com.example.socialplatform.exception.PostNotFoundException;
import com.example.socialplatform.exception.TagNotFoundException;
import com.example.socialplatform.mapper.PostMapper;
import com.example.socialplatform.repository.PostRepository;
import com.example.socialplatform.repository.TagRepository;
import com.example.socialplatform.repository.UserRepository;
import com.example.socialplatform.schema.Post;
import com.example.socialplatform.schema.Tag;
import com.example.socialplatform.schema.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Tag tag = tagRepository.findByName(postRequest.getTagName())
                .orElseThrow(() -> new TagNotFoundException(postRequest.getTagName()));
        postRepository.save(postMapper.map(postRequest, tag, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException(tagId.toString()));
        List<Post> posts = postRepository.findAllByTag(tag);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByTag(String tagName) {
        return tagRepository.findByName(tagName)
                .map(postRepository::findAllByTag)
                .orElse(Collections.emptyList())
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
