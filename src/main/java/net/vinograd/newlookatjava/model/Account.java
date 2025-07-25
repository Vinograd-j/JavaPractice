package net.vinograd.newlookatjava.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double moneyAmount;

    public Account(User user, double moneyAmount) {
        this.user = user;
        this.moneyAmount = moneyAmount;
    }

    public void addMoney(int amount){
        moneyAmount += amount;
    }

    public void withdrawMoney(double amount){
        moneyAmount -= amount;
    }

}