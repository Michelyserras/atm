package com.ifsp.atm.repository;

import com.ifsp.atm.model.Transaction;
import com.ifsp.atm.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCard(Card card);
    List<Transaction> findByCardAndTransactionDateBetween(Card card, LocalDateTime start, LocalDateTime end);
    List<Transaction> findByCardInAndTransactionDateBetween(List<Card> cards, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT t FROM Transaction t WHERE t.card IN :cards AND t.transactionDate BETWEEN :start AND :end ORDER BY t.transactionDate DESC")
    List<Transaction> findTransactionsByCardsAndDateRange(@Param("cards") List<Card> cards, 
                                                         @Param("start") LocalDateTime start, 
                                                         @Param("end") LocalDateTime end);
}
