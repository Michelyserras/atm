package com.ifsp.atm.repository;

import com.ifsp.atm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);
    Optional<User> findByCnpj(String cnpj);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByCnpj(String cnpj);
}
