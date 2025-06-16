package com.ifsp.atm.factory;

import com.ifsp.atm.model.User;

public class FreePJAccount extends BaseAccount {
    
    public FreePJAccount(User user) {
        super(user);
    }
    
    @Override
    public String getAccountType() {
        return "Free PJ";
    }
    
    @Override
    public boolean canCreateCard() {
        return user.getCards() == null || user.getCards().size() < 2;
    }
    
    @Override
    public int getMaxCardsAllowed() {
        return 2;
    }
    
    @Override
    public boolean canAccessAdvancedReports() {
        return false;
    }
    
    @Override
    public boolean canManageUsers() {
        return false;
    }
}
