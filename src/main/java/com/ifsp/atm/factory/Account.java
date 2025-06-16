package com.ifsp.atm.factory;

import com.ifsp.atm.model.User;

/**
 * Interface que define um usuário do sistema
 */
public interface Account {
    String getAccountType();
    boolean canCreateCard();
    int getMaxCardsAllowed();
    boolean canAccessAdvancedReports();
    boolean canManageUsers();
    User getUser();
    void setUser(User user);
}
