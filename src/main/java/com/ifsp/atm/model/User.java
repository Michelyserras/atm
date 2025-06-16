package com.ifsp.atm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType; // ADMIN, PREMIUM, FREE
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PersonType personType; // PJ, CLT
    
    // Dados PJ
    @Column(unique = true)
    private String cnpj;
    
    @Column
    private String companyName;
    
    // Dados CLT
    @Column(unique = true)
    private String cpf;
    
    @Column
    private String fullName;
    
    @Column(nullable = false)
    private boolean active = true;
    
    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;
    
    public enum UserType {
        ADMIN, PREMIUM, FREE
    }
    
    public enum PersonType {
        PJ, CLT
    }
}
