package com.ifsp.atm.factory;

import com.ifsp.atm.model.User;

/**
 * Factory para contas de Pessoa Física (CLT)
 */
public class CLTAccountFactory extends AccountFactory {
    
    @Override
    public Account createAccount(User.UserType userType, User user) {
        switch (userType) {
            case ADMIN:
                return new AdminCLTAccount(user);
            case PREMIUM:
                return new PremiumCLTAccount(user);
            case FREE:
                return new FreeCLTAccount(user);
            default:
                throw new IllegalArgumentException("Tipo de usuário não suportado: " + userType);
        }
    }
}
