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

    @Column(name = "login", nullable = false, unique = true)
    @Getter
    private String login;

    @Column(name = "password", nullable = false)
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