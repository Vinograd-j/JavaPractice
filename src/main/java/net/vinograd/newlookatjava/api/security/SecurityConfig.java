package net.vinograd.newlookatjava.api.security;

import lombok.RequiredArgsConstructor;
import net.vinograd.newlookatjava.api.security.components.JwtAuthenticationProvider;
import net.vinograd.newlookatjava.api.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final JwtAuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(customizer ->
                         customizer.requestMatchers("/auth/**")
                                                                 .permitAll()
                                                                 .anyRequest()
                                                                 .authenticated()

                ).sessionManagement(customizer ->
                    customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).authenticationProvider(authProvider)
                  .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
