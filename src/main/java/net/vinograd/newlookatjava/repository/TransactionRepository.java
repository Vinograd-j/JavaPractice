package net.vinograd.newlookatjava.repository;

import net.vinograd.newlookatjava.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTransactionsByAccountId(Integer accountId);

    List<Transaction> findTransactionsByTransactionDateAfterAndAccountId(LocalDateTime transactionDateAfter, Integer accountId);

}