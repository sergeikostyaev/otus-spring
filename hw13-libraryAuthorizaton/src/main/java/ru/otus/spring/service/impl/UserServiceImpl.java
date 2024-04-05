package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.User;
import ru.otus.spring.repository.UserRepository;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findFirstByUserLogin(username)
                .flatMap(user -> {
                    UserDetails userDetails = new UserDetails() {
                        @Override
                        public Collection<? extends GrantedAuthority> getAuthorities() {
                            return Set.of(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));
                        }

                        @Override
                        public String getPassword() {
                            return user.getUserPassword();
                        }

                        @Override
                        public String getUsername() {
                            return user.getUserLogin();
                        }

                        @Override
                        public boolean isAccountNonExpired() {
                            return true;
                        }

                        @Override
                        public boolean isAccountNonLocked() {
                            return true;
                        }

                        @Override
                        public boolean isCredentialsNonExpired() {
                            return true;
                        }

                        @Override
                        public boolean isEnabled() {
                            return true;
                        }
                    };

                    return Mono.just(userDetails);
                });
    }
}
