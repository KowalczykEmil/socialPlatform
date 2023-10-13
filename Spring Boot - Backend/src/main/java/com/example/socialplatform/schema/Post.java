package com.example.socialplatform.schema;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

//@Data
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long postId;
    @NotBlank(message = "Nazwa postu nie może być pusta")
    private String postName;
    @Nullable
    private String url;
    @Nullable
    @Lob
    private String description;
    private Integer voteCount = 0;
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public void upvote() {
        if (this.voteCount == -1) {
            this.voteCount = 1;
        } else {
            this.voteCount = this.voteCount + 1;
        }
    }

    public void downvote() {
        if (this.voteCount == 1) {
            this.voteCount = -1;
        } else {
            this.voteCount = this.voteCount - 1;
        }
    }
}
