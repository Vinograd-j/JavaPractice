package net.vinograd.newlookatjava.api.security;

import net.vinograd.newlookatjava.api.security.components.JwtAuthenticationProvider;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration {

    @Bean
    public JwtAuthenticationProvider authenticationProvider(UserService service, PasswordEncoder passwordEncoder){
        return new JwtAuthenticationProvider(service, passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
