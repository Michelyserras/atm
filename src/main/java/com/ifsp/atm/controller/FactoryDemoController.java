package com.ifsp.atm.controller;

import com.ifsp.atm.factory.Account;
import com.ifsp.atm.factory.AccountFactory;
import com.ifsp.atm.model.User;
import com.ifsp.atm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/factory")
public class FactoryDemoController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/account-info/{username}")
    public ResponseEntity<?> getAccountInfo(@PathVariable String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        
        User user = userOpt.get();
        
        // Demonstração do Abstract Factory Pattern
        AccountFactory factory = AccountFactory.getFactory(user.getPersonType());
        Account account = factory.createAccount(user.getUserType(), user);
        
        AccountInfo accountInfo = new AccountInfo(
            account.getAccountType(),
            user.getPersonType().toString(),
            user.getUserType().toString(),
            account.canCreateCard(),
            account.getMaxCardsAllowed(),
            account.canAccessAdvancedReports(),
            account.canManageUsers(),
            user.getPersonType() == User.PersonType.PJ ? user.getCnpj() : user.getCpf(),
            user.getPersonType() == User.PersonType.PJ ? user.getCompanyName() : user.getFullName()
        );
        
        return ResponseEntity.ok(accountInfo);
    }
    
    @GetMapping("/demo-factory")
    public ResponseEntity<?> demonstrateFactory() {
        StringBuilder demo = new StringBuilder();
        demo.append("=== DEMONSTRAÇÃO DO ABSTRACT FACTORY PATTERN ===\n\n");
        
        // Demonstrar criação de contas PJ
        demo.append("1. PESSOA JURÍDICA (PJ):\n");
        demo.append("   - AdminPJAccount: Sem limite de cartões, acesso total\n");
        demo.append("   - PremiumPJAccount: Sem limite de cartões, relatórios avançados\n");
        demo.append("   - FreePJAccount: Máximo 2 cartões, relatórios básicos\n\n");
        
        // Demonstrar criação de contas CLT
        demo.append("2. PESSOA FÍSICA (CLT):\n");
        demo.append("   - AdminCLTAccount: Sem limite de cartões, acesso total\n");
        demo.append("   - PremiumCLTAccount: Sem limite de cartões, relatórios avançados\n");
        demo.append("   - FreeCLTAccount: Máximo 2 cartões, relatórios básicos\n\n");
        
        demo.append("3. COMO USAR:\n");
        demo.append("   - POST /api/auth/login - Fazer login\n");
        demo.append("   - GET /api/factory/account-info/{username} - Ver informações da conta\n");
        demo.append("   - POST /api/cards - Criar cartão (respeitando limites)\n\n");
        
        demo.append("4. USUÁRIOS DE TESTE:\n");
        demo.append("   - admin_pj / 123456 (Admin PJ)\n");
        demo.append("   - premium_clt / 123456 (Premium CLT)\n");
        demo.append("   - free_clt / 123456 (Free CLT)\n");
        
        return ResponseEntity.ok(demo.toString());
    }
    
    // DTO
    public static class AccountInfo {
        private String accountType;
        private String personType;
        private String userType;
        private boolean canCreateCard;
        private int maxCardsAllowed;
        private boolean canAccessAdvancedReports;
        private boolean canManageUsers;
        private String document;
        private String name;
        
        public AccountInfo(String accountType, String personType, String userType, 
                          boolean canCreateCard, int maxCardsAllowed, 
                          boolean canAccessAdvancedReports, boolean canManageUsers,
                          String document, String name) {
            this.accountType = accountType;
            this.personType = personType;
            this.userType = userType;
            this.canCreateCard = canCreateCard;
            this.maxCardsAllowed = maxCardsAllowed;
            this.canAccessAdvancedReports = canAccessAdvancedReports;
            this.canManageUsers = canManageUsers;
            this.document = document;
            this.name = name;
        }
        
        // Getters
        public String getAccountType() { return accountType; }
        public String getPersonType() { return personType; }
        public String getUserType() { return userType; }
        public boolean isCanCreateCard() { return canCreateCard; }
        public int getMaxCardsAllowed() { return maxCardsAllowed; }
        public boolean isCanAccessAdvancedReports() { return canAccessAdvancedReports; }
        public boolean isCanManageUsers() { return canManageUsers; }
        public String getDocument() { return document; }
        public String getName() { return name; }
    }
}
