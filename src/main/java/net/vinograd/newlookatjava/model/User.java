package net.vinograd.newlookatjava.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Getter
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Account> accounts;

    public User(String login, String password, List<Account> accounts) {
        this.login = login;
        this.password = password;
        this.accounts = accounts;
    }

    public List<Account> getAccounts(){
        return Collections.unmodifiableList(accounts);
    }

    public void removeAccount(Account account){
        this.accounts.remove(account);
    }

}