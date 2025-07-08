package net.vinograd.newlookatjava.service;

import net.vinograd.newlookatjava.model.Account;
import net.vinograd.newlookatjava.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final Map<Integer, User> users;

    private int idCounter;

    private final AccountService accountService;

    public UserService(AccountService accountService){
        this.accountService = accountService;
        this.users = new HashMap<>();
        this.idCounter = 0;
    }

    public User createNewUser(String login){
        if (users.values().stream().anyMatch(x -> x.getLogin().equals(login)))
            throw new IllegalArgumentException("This login is already taken (login = %s)".formatted(login));

        User user = new User(++idCounter, login, new ArrayList<>());
        Account firstUserAccount = accountService.createNewAccount(user);
        user.addAccount(firstUserAccount);

        users.put(idCounter, user);

        return user;
    }

    public Optional<User> findUserById(int id){
        return Optional.ofNullable(users.get(id));
    }

    public List<User> getAllUsers(){
        return users.values().stream().toList();
    }

}