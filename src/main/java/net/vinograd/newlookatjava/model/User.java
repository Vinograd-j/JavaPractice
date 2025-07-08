package net.vinograd.newlookatjava.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class User {

    @Getter
    private final int id;

    @Getter
    private final String login;

    private List<Account> accounts;

    public void addAccount(Account account){
        accounts.add(account);
    }

    public void removeAccount(Account account){
        accounts.remove(account);
    }

    public List<Account> getAccounts(){
        return Collections.unmodifiableList(accounts);
    }

}