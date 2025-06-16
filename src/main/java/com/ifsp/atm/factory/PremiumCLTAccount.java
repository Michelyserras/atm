package com.ifsp.atm.factory;

import com.ifsp.atm.model.User;

public class PremiumCLTAccount extends BaseAccount {
    
    public PremiumCLTAccount(User user) {
        super(user);
    }
    
    @Override
    public String getAccountType() {
        return "Premium CLT";
    }
    
    @Override
    public boolean canCreateCard() {
        return true;
    }
    
    @Override
    public int getMaxCardsAllowed() {
        return Integer.MAX_VALUE; // Sem limite para premium
    }
    
    @Override
    public boolean canAccessAdvancedReports() {
        return true;
    }
    
    @Override
    public boolean canManageUsers() {
        return false;
    }
}
