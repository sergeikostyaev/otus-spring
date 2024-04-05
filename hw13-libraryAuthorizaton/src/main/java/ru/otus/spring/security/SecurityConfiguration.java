package ru.otus.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain springWebFilterChain( ServerHttpSecurity http ) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/users/**").permitAll()
                        .pathMatchers("/").authenticated()
                        .pathMatchers("books/**").authenticated()
                        .pathMatchers(HttpMethod.GET, "api/**" ).hasAnyRole("USER", "ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "api/**" ).hasRole("ADMIN")
                        .pathMatchers(HttpMethod.POST, "api/books/**" ).hasRole("ADMIN")
                        .pathMatchers(HttpMethod.POST, "api/comments/**" ).hasAnyRole("USER", "ADMIN")

                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
