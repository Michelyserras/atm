package com.ifsp.atm.controller;

import com.ifsp.atm.factory.Account;
import com.ifsp.atm.factory.AccountFactory;
import com.ifsp.atm.model.Card;
import com.ifsp.atm.model.User;
import com.ifsp.atm.repository.UserRepository;
import com.ifsp.atm.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    
    @Autowired
    private CardService cardService;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody CardRequest cardRequest) {
        try {
            // Buscar usuário
            Optional<User> userOpt = userRepository.findByUsername(cardRequest.getUsername());
            if (!userOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Usuário não encontrado");
            }
            
            User user = userOpt.get();
            AccountFactory factory = AccountFactory.getFactory(user.getPersonType());
            Account account = factory.createAccount(user.getUserType(), user);
            
            Card card = new Card();
            card.setCardNumber(cardRequest.getCardNumber());
            card.setCardHolderName(cardRequest.getCardHolderName());
            card.setCardType(cardRequest.getCardType());
            card.setCardBrand(cardRequest.getCardBrand());
            
            Card savedCard = cardService.createCard(account, card);
            return ResponseEntity.ok(savedCard);
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserCards(@PathVariable String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        
        List<Card> cards = cardService.getUserCards(userOpt.get());
        return ResponseEntity.ok(cards);
    }
    
    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deactivateCard(@PathVariable Long cardId, @RequestParam String username) {
        try {
            Optional<User> userOpt = userRepository.findByUsername(username);
            if (!userOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Usuário não encontrado");
            }
            
            User user = userOpt.get();
            AccountFactory factory = AccountFactory.getFactory(user.getPersonType());
            Account account = factory.createAccount(user.getUserType(), user);
            
            Card deactivatedCard = cardService.deactivateCard(cardId, account);
            return ResponseEntity.ok(deactivatedCard);
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // DTO
    public static class CardRequest {
        private String username;
        private String cardNumber;
        private String cardHolderName;
        private Card.CardType cardType;
        private Card.CardBrand cardBrand;
        
        // Getters e Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getCardNumber() { return cardNumber; }
        public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
        public String getCardHolderName() { return cardHolderName; }
        public void setCardHolderName(String cardHolderName) { this.cardHolderName = cardHolderName; }
        public Card.CardType getCardType() { return cardType; }
        public void setCardType(Card.CardType cardType) { this.cardType = cardType; }
        public Card.CardBrand getCardBrand() { return cardBrand; }
        public void setCardBrand(Card.CardBrand cardBrand) { this.cardBrand = cardBrand; }
    }
}
