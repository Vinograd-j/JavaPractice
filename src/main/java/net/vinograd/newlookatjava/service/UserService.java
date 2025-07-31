package net.vinograd.newlookatjava.service;

import jakarta.transaction.Transactional;
import net.vinograd.newlookatjava.model.Account;
import net.vinograd.newlookatjava.model.User;
import net.vinograd.newlookatjava.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final AccountService accountService;

    public UserService(AccountService accountService, UserRepository userRepository){
        this.accountService = accountService;
        this.userRepository = userRepository;
    }

    @Transactional
    public User createNewUser(String login, String password){
        if (this.userRepository.findUserByLogin(login).isPresent())
            throw new IllegalArgumentException("This login is already taken (login = %s)".formatted(login));

        User user = new User(login, password, new ArrayList<>());

        this.userRepository.save(user);
        accountService.createNewAccount(user);

        return user;
    }

    @Transactional
    public void removeAccount(Account account){
        account.getUser().removeAccount(account);
        this.userRepository.save(account.getUser());
    }

    public Optional<User> findUserById(int id){
        return this.userRepository.findById(id);
    }

    public Optional<User> findUserByLogin(String login){
        return this.userRepository.findUserByLogin(login);
    }

    @Transactional
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

}