package net.vinograd.newlookatjava.api.security.controllers;

import lombok.RequiredArgsConstructor;
import net.vinograd.newlookatjava.api.security.dtos.AuthRequest;
import net.vinograd.newlookatjava.api.security.dtos.AuthResponse;
import net.vinograd.newlookatjava.api.security.dtos.RegisterRequest;
import net.vinograd.newlookatjava.api.security.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(this.authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(this.authenticationService.auth(request));
    }

}
