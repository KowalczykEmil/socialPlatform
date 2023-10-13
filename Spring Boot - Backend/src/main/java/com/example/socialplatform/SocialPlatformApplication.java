package com.example.socialplatform;

import com.example.socialplatform.repository.*;
import com.example.socialplatform.schema.ERole;
import com.example.socialplatform.schema.Role;
import com.example.socialplatform.schema.Tag;
import com.example.socialplatform.schema.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableAsync
public class SocialPlatformApplication implements CommandLineRunner {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    RateRepository rateRepository;
    @Autowired
    PostRepository postRepository;

    public static void main(String[] args) {
        SpringApplication.run(SocialPlatformApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
  /* if (!roleRepository.findByName(ERole.ROLE_USER).isPresent()) {
          roleRepository.save(new Role(1, ERole.ROLE_USER));
     }
   if (!roleRepository.findByName(ERole.ROLE_ADMIN).isPresent()) {
       roleRepository.save(new Role(1, ERole.ROLE_ADMIN));    }
      final Optional<Role> byName = roleRepository.findByName(ERole.ROLE_ADMIN);
 Set<Role> roles = new HashSet<>();
 roles.add(byName.get());
 if (!userRepository.findByUsername("admin").isPresent()) {
       User user = new User();
          user.setUsername("admin");
          user.setEmail("admin@example.com");
         user.setPassword(passwordEncoder.encode("123456"));
        user.setCreated(Instant.now());
         user.setEnabled(true);
         user.setRoles(roles);
         userRepository.save(user);
      }*/
//        if (!tagRepository.findByName("sport").isPresent()) {
//            tagRepository.save(Tag.builder().name("sport").description("dyskusje o sporcie").build());
//        }
//        if (!tagRepository.findByName("polityka").isPresent()) {
//            tagRepository.save(Tag.builder().name("polityka").description("dyskusje o polityce").build());
//        }

    }
}
