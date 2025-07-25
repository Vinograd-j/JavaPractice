package net.vinograd.newlookatjava.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "client")
public class User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private String login;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Account> accounts;

    public User(String login, List<Account> accounts) {
        this.login = login;
        this.accounts = accounts;
    }

    public User(Integer id, String login) {
        this.id = id;
        this.login = login;
        this.accounts = new ArrayList<>();
    }

    public List<Account> getAccounts(){
        return Collections.unmodifiableList(accounts);
    }

    public void removeAccount(Account account){
        this.accounts.remove(account);
    }

}