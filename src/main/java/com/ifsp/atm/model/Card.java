package com.ifsp.atm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String cardNumber;
    
    @Column(nullable = false)
    private String cardHolderName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType; // DEBIT, CREDIT
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardBrand cardBrand; // VISA, MASTERCARD, ELO, AMERICAN_EXPRESS
    
    @Column(nullable = false)
    private boolean active = true;
    
    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;
    
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
    
    public enum CardType {
        DEBIT, CREDIT
    }
    
    public enum CardBrand {
        VISA, MASTERCARD, ELO, AMERICAN_EXPRESS
    }
}
