package com.example.socialplatform.schema;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;


//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_account")
public class User {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @NotBlank(message = "Nazwa użytkownika jest wymagana")
    private String username;
    @NotBlank(message = "Hasło użytkownika jest wymagane")
    private String password;
    @Email
    @NotEmpty(message = "Adres e-mail jest wymagany")
    private String email;
    private Instant created;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
