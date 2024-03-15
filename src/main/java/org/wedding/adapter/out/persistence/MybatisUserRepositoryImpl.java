package org.wedding.adapter.out.persistence;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.wedding.domain.user.User;
import org.wedding.application.port.out.repository.UserRepository;

@Repository
@Mapper
public interface MybatisUserRepositoryImpl extends UserRepository {
    @Override
    int save(User user);

    @Override
    Optional<User> findById(int id);

    @Override
    Optional<User> findByEmail(String email);

    @Override
    boolean existsById(int id);

    @Override
    boolean existsByEmail(String email);
}
