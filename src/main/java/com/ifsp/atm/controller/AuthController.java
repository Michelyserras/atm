package com.ifsp.atm.controller;

import com.ifsp.atm.factory.Account;
import com.ifsp.atm.model.User;
import com.ifsp.atm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Account> account = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        
        if (account.isPresent()) {
            return ResponseEntity.ok(new LoginResponse(
                account.get().getAccountType(),
                account.get().getUser().getUsername(),
                account.get().canCreateCard(),
                account.get().getMaxCardsAllowed(),
                account.get().canAccessAdvancedReports(),
                account.get().canManageUsers()
            ));
        }
        
        return ResponseEntity.badRequest().body("Credenciais inválidas");
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = authService.registerUser(user);
            return ResponseEntity.ok("Usuário criado com sucesso: " + savedUser.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // DTOs
    public static class LoginRequest {
        private String username;
        private String password;
        
        // Getters e Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class LoginResponse {
        private String accountType;
        private String username;
        private boolean canCreateCard;
        private int maxCardsAllowed;
        private boolean canAccessAdvancedReports;
        private boolean canManageUsers;
        
        public LoginResponse(String accountType, String username, boolean canCreateCard, 
                           int maxCardsAllowed, boolean canAccessAdvancedReports, boolean canManageUsers) {
            this.accountType = accountType;
            this.username = username;
            this.canCreateCard = canCreateCard;
            this.maxCardsAllowed = maxCardsAllowed;
            this.canAccessAdvancedReports = canAccessAdvancedReports;
            this.canManageUsers = canManageUsers;
        }
        
        // Getters
        public String getAccountType() { return accountType; }
        public String getUsername() { return username; }
        public boolean isCanCreateCard() { return canCreateCard; }
        public int getMaxCardsAllowed() { return maxCardsAllowed; }
        public boolean isCanAccessAdvancedReports() { return canAccessAdvancedReports; }
        public boolean isCanManageUsers() { return canManageUsers; }
    }
}
