package com.ifsp.atm.service;

import com.ifsp.atm.factory.Account;
import com.ifsp.atm.model.Card;
import com.ifsp.atm.model.User;
import com.ifsp.atm.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;
    
    public Card createCard(Account account, Card card) {
        User user = account.getUser();
        
        // Verificar se pode criar cartão
        if (!account.canCreateCard()) {
            throw new RuntimeException("Usuário atingiu o limite de cartões permitidos: " + account.getMaxCardsAllowed());
        }
        
        // Verificar limite atual
        long currentCards = cardRepository.countByUserAndActiveTrue(user);
        if (currentCards >= account.getMaxCardsAllowed()) {
            throw new RuntimeException("Limite de cartões atingido: " + account.getMaxCardsAllowed());
        }
        
        card.setUser(user);
        return cardRepository.save(card);
    }
    
    public List<Card> getUserCards(User user) {
        return cardRepository.findByUserAndActiveTrue(user);
    }
    
    public Card deactivateCard(Long cardId, Account account) {
        Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
            
        // Verificar se o cartão pertence ao usuário
        if (!card.getUser().getId().equals(account.getUser().getId())) {
            throw new RuntimeException("Cartão não pertence ao usuário");
        }
        
        card.setActive(false);
        return cardRepository.save(card);
    }
}
