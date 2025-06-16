package com.ifsp.atm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "currencies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 3)
    private String code; // BRL, USD, EUR
    
    @Column(nullable = false)
    private String name; // Real, Dollar, Euro
    
    @Column(nullable = false)
    private String symbol; // R$, $, €
}
