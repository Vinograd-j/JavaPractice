package net.vinograd.newlookatjava.api.security.components;

import lombok.RequiredArgsConstructor;
import net.vinograd.newlookatjava.api.exception.errors.UserNotFoundException;
import net.vinograd.newlookatjava.api.security.dtos.UserDetails;
import net.vinograd.newlookatjava.model.User;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        User user = userService.findUserByLogin(login).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!encoder.matches(rawPassword, user.getPassword()))
            throw new BadCredentialsException("Invalid password");

        return new UsernamePasswordAuthenticationToken(
                new UserDetails(user.getId(), user.getLogin()),
                null,
                List.of()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}