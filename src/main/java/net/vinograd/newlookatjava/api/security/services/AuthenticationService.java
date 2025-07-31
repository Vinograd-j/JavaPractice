package net.vinograd.newlookatjava.api.security.services;

import lombok.RequiredArgsConstructor;
import net.vinograd.newlookatjava.api.exception.errors.UserNotFoundException;
import net.vinograd.newlookatjava.api.security.components.JwtAuthenticationProvider;
import net.vinograd.newlookatjava.api.security.dtos.AuthRequest;
import net.vinograd.newlookatjava.api.security.dtos.AuthResponse;
import net.vinograd.newlookatjava.api.security.dtos.RegisterRequest;
import net.vinograd.newlookatjava.api.security.dtos.UserDetails;
import net.vinograd.newlookatjava.api.security.jwt.JwtService;
import net.vinograd.newlookatjava.model.User;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    private final JwtAuthenticationProvider authenticationProvider;


    public AuthResponse register(RegisterRequest request){
        String password = encoder.encode(request.getPassword());

        User user = this.userService.createNewUser(request.getLogin(), password);

        String jwtToken = this.jwtService.generateToken(new UserDetails(user.getId(), user.getLogin()));

        return new AuthResponse(jwtToken);
    }

    public AuthResponse auth(AuthRequest request){
        this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

        User user = this.userService.findUserByLogin(request.getLogin()).orElseThrow(() -> new UserNotFoundException("User not found"));

        String token = this.jwtService.generateToken(new UserDetails(user.getId(), user.getLogin()));

        return new AuthResponse(token);
    }

}
