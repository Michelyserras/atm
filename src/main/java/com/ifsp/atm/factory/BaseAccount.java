package com.ifsp.atm.factory;

import com.ifsp.atm.model.User;

/**
 * Classe base abstrata para todas as contas
 */
public abstract class BaseAccount implements Account {
    protected User user;
    
    public BaseAccount(User user) {
        this.user = user;
    }
    
    @Override
    public User getUser() {
        return user;
    }
    
    @Override
    public void setUser(User user) {
        this.user = user;
    }
}
