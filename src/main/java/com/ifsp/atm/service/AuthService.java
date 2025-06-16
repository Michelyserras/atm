package com.ifsp.atm.service;

import com.ifsp.atm.factory.Account;
import com.ifsp.atm.factory.AccountFactory;
import com.ifsp.atm.model.User;
import com.ifsp.atm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Optional<Account> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword()) && user.isActive()) {
                AccountFactory factory = AccountFactory.getFactory(user.getPersonType());
                Account account = factory.createAccount(user.getUserType(), user);
                return Optional.of(account);
            }
        }
        
        return Optional.empty();
    }
    
    public User registerUser(User user) {
        // Validar se username e email são únicos
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username já existe");
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email já existe");
        }
        
        // Validar documento único
        if (user.getPersonType() == User.PersonType.PJ && userRepository.existsByCnpj(user.getCnpj())) {
            throw new RuntimeException("CNPJ já existe");
        }
        
        if (user.getPersonType() == User.PersonType.CLT && userRepository.existsByCpf(user.getCpf())) {
            throw new RuntimeException("CPF já existe");
        }
        
        // Criptografar senha
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userRepository.save(user);
    }
}
