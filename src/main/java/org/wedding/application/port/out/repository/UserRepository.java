package org.wedding.application.port.out.repository;

import java.util.Optional;

import org.wedding.domain.user.User;

public interface UserRepository {
    int save(User user);

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    boolean existsById(int id);

    boolean existsByEmail(String email);
}
