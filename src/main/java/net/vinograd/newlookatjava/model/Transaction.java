package net.vinograd.newlookatjava.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    private Type type;

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "receiver_id")
    private Integer receiverId;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    public Transaction(Type type, Integer accountId, Integer receiverId, double amount, LocalDateTime transactionDate) {
        this.type = type;
        this.accountId = accountId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Transaction(Type type, Integer accountId, double amount, LocalDateTime transactionDate) {
        this.type = type;
        this.accountId = accountId;
        this.receiverId = null;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public enum Type {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

}