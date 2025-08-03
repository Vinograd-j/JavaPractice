package net.vinograd.newlookatjava.service;

import lombok.RequiredArgsConstructor;
import net.vinograd.newlookatjava.model.Transaction;
import net.vinograd.newlookatjava.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getAllAccountTransactionsPeriod(int accountId, LocalDateTime from, LocalDateTime to) {
        return this.transactionRepository.findAllByAccountIdAndTransactionDateBetween(accountId, from, to);
    }

    public List<Transaction> getAllAccountTransactions(int accountId) {
        return this.transactionRepository.findTransactionsByAccountId(accountId);
    }

    @Transactional
    public void addTransaction(Transaction transaction){
        this.transactionRepository.save(transaction);
    }

}
// localhost:8080/accounts/operations/period/1?from=2025-07-01T00:00:00&to=2025-08-01T23:59:59