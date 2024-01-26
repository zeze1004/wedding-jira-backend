package project.wedding.user.service;

import static project.wedding.user.exception.UserError.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.wedding.user.domain.User;
import project.wedding.user.exception.UserException;
import project.wedding.user.repository.UserRepository;
import project.wedding.user.repository.mybatis.dto.request.SignUpRequest;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public int signUp(SignUpRequest request) {
        checkDuplicateEmail(request);
        User user = SignUpRequest.toEntity(request);
        userRepository.save(user); // TODO: id 넣는 과정 찾아보기

        return user.getId();
    }

    private void checkDuplicateEmail(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserException(EMAIL_DUPLICATION);
        }
    }
}
