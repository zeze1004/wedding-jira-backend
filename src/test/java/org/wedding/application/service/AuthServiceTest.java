package org.wedding.application.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.wedding.adapter.in.web.dto.SignUpDTO;
import org.wedding.application.port.in.AuthUseCase;
import org.wedding.application.port.out.repository.UserRepository;
import org.wedding.domain.user.User;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private AuthUseCase authService;

    private SignUpDTO signUpDTOWithPartner;
    private SignUpDTO signUpDTOWithoutPartner;

    @BeforeEach
    void setUp() {
        signUpDTOWithPartner = new SignUpDTO(
            "test1@gmail.com",
            "password1234",
            "김커플",
            "ODeeS2",
            "testPartner@gmail.com"
        );
        signUpDTOWithoutPartner = new SignUpDTO(
            "test2@gmail.com",
            "password1234",
            "최솔로",
            "ODee1004"
        );
        authService = new AuthService(userRepository, eventPublisher, passwordEncoder);
    }

    @DisplayName("회원가입 성공 - 예비 배우자 이메일을 넣은 경우")
    @Test
    void signUpWithPartnerEmail() {
        when(userRepository.existsByEmail(signUpDTOWithPartner.email())).thenReturn(false);

        authService.signUp(signUpDTOWithPartner);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("회원가입 성공 - 예비 배우자 이메일을 안 넣은 경우")
    @Test
    void signUpWithoutPartnerEmail() {
        when(userRepository.existsByEmail(signUpDTOWithoutPartner.email())).thenReturn(false);

        authService.signUp(signUpDTOWithoutPartner);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void login() {
    }
}
