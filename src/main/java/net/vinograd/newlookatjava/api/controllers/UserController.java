package net.vinograd.newlookatjava.api.controllers;

import net.vinograd.newlookatjava.api.dtos.AccountDTO;
import net.vinograd.newlookatjava.api.dtos.UserDTO;
import net.vinograd.newlookatjava.api.exception.errors.UserNotFoundException;
import net.vinograd.newlookatjava.model.User;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public UserDTO findById(@PathVariable Integer id){
        User user = this.userService.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));

        List<AccountDTO> accounts = user.getAccounts().stream().map((account) ->
                new AccountDTO(account.getId(), account.getMoneyAmount())).toList();

        return new UserDTO(user.getId(), user.getLogin(), accounts);
    }

    @GetMapping("/users/all")
    public List<UserDTO> allUsers(){
        return this.userService.getAllUsers().stream().
                map(user -> new UserDTO(user.getId(), user.getLogin(),
                        user.getAccounts().stream().
                        map(account -> new AccountDTO(account.getId(), account.getMoneyAmount())).toList()))
                .toList();
    }

}