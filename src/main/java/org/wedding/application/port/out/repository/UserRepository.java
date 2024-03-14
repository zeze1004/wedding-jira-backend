package org.wedding.application.port.out.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.wedding.domain.user.User;

@Repository
public interface UserRepository {
    int save(User user);

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    boolean existsById(int id);

    boolean existsByEmail(String email);
}
