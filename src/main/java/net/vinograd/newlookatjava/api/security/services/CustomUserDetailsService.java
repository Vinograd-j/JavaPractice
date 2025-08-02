package net.vinograd.newlookatjava.api.security.services;

import lombok.RequiredArgsConstructor;
import net.vinograd.newlookatjava.api.exception.errors.UserNotFoundException;
import net.vinograd.newlookatjava.api.security.dtos.UserDetails;
import net.vinograd.newlookatjava.model.User;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService {

    private final UserService userService;

    public UserDetails findUserByLogin(String login){
        User user = userService.findUserByLogin(login).orElseThrow(() -> new UserNotFoundException("User with name " + login + " not found"));
        return new UserDetails(user.getId(), user.getLogin());
    }
}
