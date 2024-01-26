package project.wedding.user.repository.mybatis;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import project.wedding.user.domain.User;
import project.wedding.user.repository.UserRepository;

@Mapper
public interface UserMapper extends UserRepository {

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
