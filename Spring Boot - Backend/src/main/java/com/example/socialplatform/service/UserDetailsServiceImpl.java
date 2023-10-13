package com.example.socialplatform.service;

import com.example.socialplatform.repository.UserRepository;
import com.example.socialplatform.schema.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Nadpisujemy metodę loadUserByUsername (SpringSecurity), która służy do pobierania danych użytkownika.
     * Opakowujemy szczegóły obiektu User i opakowujemy go w Optional,i wykorzystujemy interfejs UserDetails
     * */

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("Nie ma użytkownika " +
                        "o nazwie: " + username));

        return UserDetailsImpl.build(user);
    }
}
