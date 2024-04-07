package org.wedding.application.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.adapter.in.web.dto.LoginDTO;
import org.wedding.adapter.in.web.dto.SignUpDTO;
import org.wedding.application.port.in.AuthUseCase;
import org.wedding.application.port.out.repository.UserRepository;
import org.wedding.application.service.response.LoginResponse;
import org.wedding.domain.user.User;
import org.wedding.domain.user.event.UserCreatedEvent;
import org.wedding.domain.user.exception.UserError;
import org.wedding.domain.user.exception.UserException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public int signUp(SignUpDTO request) { // TODO: SignUpDTO를 command로 맵핑
        if (userRepository.existsByEmail(request.email())) {
            throw new UserException(UserError.EMAIL_DUPLICATION);
        }

        User user = SignUpDTO.toEntity(request, passwordEncoder);

        userRepository.save(user);
        eventPublisher.publishEvent(new UserCreatedEvent(user.getId()));

        return user.getId();
    }

    @Transactional
    @Override
    public LoginResponse login(LoginDTO request) {
        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UserException(UserError.PASSWORD_NOT_MATCH);
        }

        return new LoginResponse(user.getId());
    }
}
