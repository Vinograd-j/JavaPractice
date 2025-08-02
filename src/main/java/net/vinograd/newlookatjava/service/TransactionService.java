package net.vinograd.newlookatjava.service;

import lombok.RequiredArgsConstructor;
import net.vinograd.newlookatjava.model.Transaction;
import net.vinograd.newlookatjava.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getAllAccountTransactionsPeriod(int accountId, Period period) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime timeReferencePoint = currentTime.minus(period);

        return this.transactionRepository.findTransactionsByTransactionDateAfterAndAccountId(timeReferencePoint, accountId);
    }

    public List<Transaction> getAllAccountTransactions(int accountId) {
        return this.transactionRepository.findTransactionsByAccountId(accountId);
    }

    @Transactional
    public void addTransaction(Transaction transaction){
        this.transactionRepository.save(transaction);
    }

}
