package com.example.socialplatform.service;

import com.example.socialplatform.exception.PostNotFoundException;
import com.example.socialplatform.exception.SocialPlatformException;
import com.example.socialplatform.repository.PostRepository;
import com.example.socialplatform.repository.RateRepository;
import com.example.socialplatform.schema.Post;
import com.example.socialplatform.schema.Rating;
import com.example.socialplatform.schema.RatingType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.socialplatform.schema.RatingType.DISLIKE;
import static com.example.socialplatform.schema.RatingType.LIKE;

@Service
@AllArgsConstructor
public class RatingService {

    private final RateRepository rateRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void upvote(Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Nie znaleziono wpisu z ID: " + postId));
        final Optional<Rating> topByPostAndUserOrderByVoteIdDesc =
                rateRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (topByPostAndUserOrderByVoteIdDesc.filter(it -> it.getRatingType().equals(LIKE)).isPresent()) {
            throw new SocialPlatformException("Polajkowales juz ten wpis");
        }
        post.upvote();
        final Rating rating = mapToVote(LIKE, post);
        rateRepository.save(rating);
        postRepository.save(post);
    }

    @Transactional
    public void downvote(Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Nie znaleziono wpisu z ID: " + postId));
        final Optional<Rating> topByPostAndUserOrderByVoteIdDesc =
                rateRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (topByPostAndUserOrderByVoteIdDesc.filter(it -> it.getRatingType().equals(DISLIKE)).isPresent()) {
            throw new SocialPlatformException("Zdislajkowales juz ten wpis");
        }
        post.downvote();
        final Rating rating = mapToVote(DISLIKE, post);
        rateRepository.save(rating);
        postRepository.save(post);
    }

    private Rating mapToVote(RatingType ratingType, Post post) {
        return Rating.builder()
                .ratingType(ratingType)
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }

}
