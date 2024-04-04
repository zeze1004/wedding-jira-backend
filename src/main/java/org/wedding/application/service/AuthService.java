package org.wedding.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.adapter.in.web.dto.LoginDTO;
import org.wedding.adapter.in.web.dto.SignUpDTO;
import org.wedding.application.port.in.AuthUseCase;
import org.wedding.application.port.out.repository.UserRepository;
import org.wedding.domain.user.User;
import org.wedding.domain.user.exception.UserError;
import org.wedding.domain.user.exception.UserException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public int signUp(SignUpDTO request) { // TODO: SignUpDTO를 command로 맵핑
        if (userRepository.existsByEmail(request.email())) {
            throw new UserException(UserError.EMAIL_DUPLICATION);
        }

        User user = SignUpDTO.toEntity(request, passwordEncoder);

        userRepository.save(user);

        return user.getId();
    }

    @Transactional
    @Override
    public void login(LoginDTO request, HttpSession session) {
        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UserException(UserError.PASSWORD_NOT_MATCH);
        }

        session.setAttribute("userId", user.getId());
    }
}
