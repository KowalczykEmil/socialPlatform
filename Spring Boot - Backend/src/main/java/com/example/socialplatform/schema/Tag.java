package com.example.socialplatform.schema;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

//@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    @NotBlank(message = "Nazwa tagu jest wymagana!")
    private String name;
    @NotBlank(message = "Opis tagu jest wymagany!")
    private String description;
    @OneToMany(fetch = EAGER, mappedBy = "tag", targetEntity = Post.class)
    private List<Post> posts;
    private Instant createdDate;
    @ManyToOne(fetch = EAGER)
    private User user;
}
