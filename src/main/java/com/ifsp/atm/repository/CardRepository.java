package com.ifsp.atm.repository;

import com.ifsp.atm.model.Card;
import com.ifsp.atm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByUserAndActiveTrue(User user);
    List<Card> findByUser(User user);
    long countByUserAndActiveTrue(User user);
}
