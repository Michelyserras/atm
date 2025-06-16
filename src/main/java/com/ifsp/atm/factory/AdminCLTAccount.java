package com.ifsp.atm.factory;

import com.ifsp.atm.model.User;

public class AdminCLTAccount extends BaseAccount {
    
    public AdminCLTAccount(User user) {
        super(user);
    }
    
    @Override
    public String getAccountType() {
        return "Admin CLT";
    }
    
    @Override
    public boolean canCreateCard() {
        return true;
    }
    
    @Override
    public int getMaxCardsAllowed() {
        return Integer.MAX_VALUE; // Sem limite
    }
    
    @Override
    public boolean canAccessAdvancedReports() {
        return true;
    }
    
    @Override
    public boolean canManageUsers() {
        return true;
    }
}
