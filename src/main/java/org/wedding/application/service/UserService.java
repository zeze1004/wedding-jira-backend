package org.wedding.application.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.domain.user.User;
import org.wedding.domain.user.exception.UserError;
import org.wedding.application.port.out.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.wedding.domain.user.exception.UserException;
import org.wedding.adapter.in.web.dto.UserDto;
import org.wedding.adapter.in.web.dto.LoginDTO;
import org.wedding.adapter.in.web.dto.SignUpDTO;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    // private final HttpSession session;  // TODO: session 클래스로 분리하기

    @Transactional
    public int signUp(SignUpDTO request) {
        checkDuplicateEmail(request);
        User user = SignUpDTO.toEntity(request);
        userRepository.save(user); // TODO: id 넣는 과정 찾아보기

        return user.getId();
    }

    private void checkDuplicateEmail(SignUpDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserException(UserError.EMAIL_DUPLICATION);
        }
    }

    // TODO: UserDto를 User로 바꾸기
    public void login(LoginDTO request) {
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
