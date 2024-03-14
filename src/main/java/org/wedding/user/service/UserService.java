package org.wedding.user.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.domain.User;
import org.wedding.user.exception.UserError;
import org.wedding.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.wedding.user.exception.UserException;
import org.wedding.user.repository.mybatis.dto.UserDto;
import org.wedding.user.repository.mybatis.dto.request.LoginRequest;
import org.wedding.user.repository.mybatis.dto.request.SignUpRequest;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    // private final HttpSession session;  // TODO: session 클래스로 분리하기

    @Transactional
    public int signUp(SignUpRequest request) {
        checkDuplicateEmail(request);
        User user = SignUpRequest.toEntity(request);
        userRepository.save(user); // TODO: id 넣는 과정 찾아보기

        return user.getId();
    }

    private void checkDuplicateEmail(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserException(UserError.EMAIL_DUPLICATION);
        }
    }

    // TODO: UserDto를 User로 바꾸기
    public void login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        if (!equals(user.getPassword(), request.password())) {
            throw new UserException(UserError.PASSWORD_NOT_MATCH);
        }

        UserDto.of(user);

    //     session.setAttribute("user", user.getId());
    //     System.out.println("session.getId() = " + session.getId());
    }

    private boolean equals(String password, String password1) {
        return Objects.equals(password, password1);
    }
}
