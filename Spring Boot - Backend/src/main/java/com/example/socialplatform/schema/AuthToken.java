package com.example.socialplatform.schema;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class AuthToken {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    private String token;
    @OneToOne(fetch = EAGER)
    private User user;
    private Instant expiryDate;
}
