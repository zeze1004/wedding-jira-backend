package org.wedding.user.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.wedding.domain.User;

@Repository
public interface UserRepository {
    int save(User user);

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    boolean existsById(int id);

    boolean existsByEmail(String email);
}
