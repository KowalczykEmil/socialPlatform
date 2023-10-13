package com.example.socialplatform.mapper;

import com.example.socialplatform.datatransferobject.PostRequest;
import com.example.socialplatform.datatransferobject.PostResponse;
import com.example.socialplatform.repository.CommentRepository;
import com.example.socialplatform.repository.RateRepository;
import com.example.socialplatform.schema.*;
import com.example.socialplatform.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.example.socialplatform.schema.RatingType.DISLIKE;
import static com.example.socialplatform.schema.RatingType.LIKE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private AuthService authService;


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "tag", source = "tag")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Tag tag, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "tagName", source = "tag.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "like", expression = "java(isPostLiked(post))")
    @Mapping(target = "disLike", expression = "java(isPostDisLiked(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostLiked(Post post) {
        return checkVoteType(post, LIKE);
    }

    boolean isPostDisLiked(Post post) {
        return checkVoteType(post, DISLIKE);
    }

    private boolean checkVoteType(Post post, RatingType ratingType) {
        if (authService.isLoggedIn()) {
            Optional<Rating> voteForPostByUser =
                    rateRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(rating -> rating.getRatingType().equals(ratingType))
                    .isPresent();
        }
        return false;
    }

}
