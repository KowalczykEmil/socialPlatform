package com.example.socialplatform.service;

import com.example.socialplatform.JWT.JwtProvider;
import com.example.socialplatform.datatransferobject.AuthenticationResponse;
import com.example.socialplatform.datatransferobject.LoginRequest;
import com.example.socialplatform.datatransferobject.RefreshTokenRequest;
import com.example.socialplatform.datatransferobject.RegisterRequest;
import com.example.socialplatform.exception.SocialPlatformException;
import com.example.socialplatform.repository.RoleRepository;
import com.example.socialplatform.repository.UserRepository;
import com.example.socialplatform.repository.VerificationTokenRepository;
import com.example.socialplatform.schema.ERole;
import com.example.socialplatform.schema.NotificationEmail;
import com.example.socialplatform.schema.Role;
import com.example.socialplatform.schema.User;
import com.example.socialplatform.schema.AuthToken;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private final RoleRepository roleRepository;

    public void register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        final Optional<Role> byName = roleRepository.findByName(ERole.ROLE_USER);
        user.setRoles(Set.of(byName.get()));
        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Aktywuj swoje konto w serwisie SocialPlatform.pl!",
                user.getEmail(),
                "Witaj w naszym serwisie! Dziękujemy za zarejestrowanie się. Aby aktywować swoje konto, prosimy o kliknięcie w poniższy link " +
                        "http://localhost:8080/api/v1/autoryzacja/zweryfikujkonto/" +  token + " Po klinięciu w link, będziesz mógł/mogła korzystać w pełni z możliwości naszego serwisu" +
                        "Z poważaniem" + "Założyciel SocialPlatform"));
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono nazwy użytkownika - " + principal.getSubject()));
    }

    private void fetchUserAndEnable(AuthToken authToken) {
        String username = authToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SocialPlatformException("Nie znaleziono użytkownika z nazwą: " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setUser(user);

        verificationTokenRepository.save(authToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<AuthToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SocialPlatformException("Nieprawidłowy token")));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String username = refreshTokenRequest.getUsername();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtProvider.generateTokenWithUserName(userDetails);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
