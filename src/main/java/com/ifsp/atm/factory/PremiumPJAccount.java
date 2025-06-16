package com.ifsp.atm.factory;

import com.ifsp.atm.model.User;

public class PremiumPJAccount extends BaseAccount {
    
    public PremiumPJAccount(User user) {
        super(user);
    }
    
    @Override
    public String getAccountType() {
        return "Premium PJ";
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
