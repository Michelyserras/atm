package com.ifsp.atm.service;

import com.ifsp.atm.factory.Account;
import com.ifsp.atm.model.Card;
import com.ifsp.atm.model.Transaction;
import com.ifsp.atm.repository.CardRepository;
import com.ifsp.atm.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private CardRepository cardRepository;
    
    public Transaction createTransaction(Account account, Transaction transaction) {
        Card card = transaction.getCard();
        
        // Verificar se o cartão pertence ao usuário
        if (!card.getUser().getId().equals(account.getUser().getId())) {
            throw new RuntimeException("Cartão não pertence ao usuário");
        }
        
        return transactionRepository.save(transaction);
    }
    
    public List<Transaction> getTransactionsByDateRange(Account account, LocalDateTime start, LocalDateTime end) {
        List<Card> userCards = cardRepository.findByUserAndActiveTrue(account.getUser());
        return transactionRepository.findTransactionsByCardsAndDateRange(userCards, start, end);
    }
    
    public BigDecimal getTotalSpentInPeriod(Account account, LocalDateTime start, LocalDateTime end) {
        List<Transaction> transactions = getTransactionsByDateRange(account, start, end);
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public List<Transaction> getTransactionsByCard(Card card, Account account) {
        // Verificar se o cartão pertence ao usuário
        if (!card.getUser().getId().equals(account.getUser().getId())) {
            throw new RuntimeException("Cartão não pertence ao usuário");
        }
        
        return transactionRepository.findByCard(card);
    }
}
