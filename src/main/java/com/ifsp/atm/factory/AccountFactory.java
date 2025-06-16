package com.ifsp.atm.factory;

import com.ifsp.atm.model.User;

/**
 * Factory abstrata para criação de contas
 */
public abstract class AccountFactory {
    
    public abstract Account createAccount(User.UserType userType, User user);
    
    public static AccountFactory getFactory(User.PersonType personType) {
        switch (personType) {
            case PJ:
                return new PJAccountFactory();
            case CLT:
                return new CLTAccountFactory();
            default:
                throw new IllegalArgumentException("Tipo de pessoa não suportado: " + personType);
        }
    }
}
