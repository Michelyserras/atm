package com.ifsp.atm.factory;

import com.ifsp.atm.model.User;

/**
 * Factory para contas de Pessoa Jurídica (PJ)
 */
public class PJAccountFactory extends AccountFactory {
    
    @Override
    public Account createAccount(User.UserType userType, User user) {
        switch (userType) {
            case ADMIN:
                return new AdminPJAccount(user);
            case PREMIUM:
                return new PremiumPJAccount(user);
            case FREE:
                return new FreePJAccount(user);
            default:
                throw new IllegalArgumentException("Tipo de usuário não suportado: " + userType);
        }
    }
}
