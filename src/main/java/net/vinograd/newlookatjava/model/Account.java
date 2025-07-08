package net.vinograd.newlookatjava.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Account {

    private int id;

    private int userId;

    private double moneyAmount;

    public void addMoney(int amount){
        moneyAmount += amount;
    }

    public void withdrawMoney(double amount){
        moneyAmount -= amount;
    }

}