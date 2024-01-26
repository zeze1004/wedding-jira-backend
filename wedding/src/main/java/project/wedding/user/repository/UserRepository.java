package project.wedding.user.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import project.wedding.user.domain.User;

@Repository
public interface UserRepository {
    int save(User user);

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    boolean existsById(int id);

    boolean existsByEmail(String email);
}
