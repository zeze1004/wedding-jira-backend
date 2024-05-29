package org.wedding.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.wedding.adapter.in.web.dto.LoginDTO;
import org.wedding.adapter.in.web.dto.SignUpDTO;
import org.wedding.application.port.out.repository.UserRepository;
import org.wedding.application.service.response.LoginResponse;
import org.wedding.domain.user.User;
import org.wedding.domain.user.event.CreateCardBoardAfterCreatedUserEvent;
import org.wedding.domain.user.exception.UserException;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    private SignUpDTO signUpDTOWithPartner;
    private SignUpDTO signUpDTOWithoutPartner;
    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        signUpDTOWithPartner = new SignUpDTO("test1@gmail.com", "password1234", "김커플", "ODeeS2",
            "testPartner@gmail.com");

        signUpDTOWithoutPartner = new SignUpDTO("test2@gmail.com", "password1234", "최솔로", "ODee1004");

        loginDTO = new LoginDTO("test@example.com", "password1234!!");

        authService = new AuthService(userRepository, eventPublisher, passwordEncoder);
    }

    @DisplayName("회원가입 성공 - 예비 배우자 이메일을 넣은 경우")
    @Test
    void signUpWithPartnerEmail() {
        lenient().when(userRepository.existsByEmail(signUpDTOWithPartner.email())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        authService.signUp(signUpDTOWithPartner);

        verify(userRepository, times(1)).save(any(User.class));
        verify(eventPublisher, times(1)).publishEvent(any(CreateCardBoardAfterCreatedUserEvent.class));
    }

    @DisplayName("회원가입 성공 - 예비 배우자 이메일을 안 넣은 경우")
    @Test
    void signUpWithoutPartnerEmail() {
        lenient().when(userRepository.existsByEmail(signUpDTOWithoutPartner.email())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        authService.signUp(signUpDTOWithoutPartner);

        verify(userRepository, times(1)).save(any(User.class));
        verify(eventPublisher, times(1)).publishEvent(any(CreateCardBoardAfterCreatedUserEvent.class));
    }

    @DisplayName("로그인 성공")
    @Test
    void loginSuccess() {
        User user = User.builder().email("test1@gmail.com").password("encodedPassword").build();

        when(userRepository.findByEmail(loginDTO.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDTO.password(), user.getPassword())).thenReturn(true);

        LoginResponse response = authService.login(loginDTO);

        assertNotNull(response);
        verify(userRepository, times(1)).findByEmail(loginDTO.email());
    }

    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    @Test
    void loginFailUserNotFound() {
        when(userRepository.findByEmail(loginDTO.email())).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> authService.login(loginDTO));

        verify(userRepository, times(1)).findByEmail(loginDTO.email());
    }

    @DisplayName("로그인 실패 - 비밀번호 불일치")
    @Test
    void loginFailPasswordNotMatch() {
        User user = User.builder().email("test1@gmail.com").password("encodedPassword").build();

        when(userRepository.findByEmail(loginDTO.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDTO.password(), user.getPassword())).thenReturn(false);

        assertThrows(UserException.class, () -> authService.login(loginDTO));

        verify(userRepository, times(1)).findByEmail(loginDTO.email());
    }

    @DisplayName("이메일 중복 검사 성공 - 중복된 이메일")
    @Test
    void isEmailExist_DuplicatedEmail() {
        when(userRepository.existsByEmail(signUpDTOWithPartner.email())).thenReturn(true);

        assertThrows(UserException.class, () -> authService.isEmailExist(signUpDTOWithPartner.email()));

        verify(userRepository, times(1)).existsByEmail(signUpDTOWithPartner.email());
    }

    @DisplayName("이메일 중복 검사 성공 - 사용 가능한 이메일")
    @Test
    void isEmailExist_AvailableEmail() {
        when(userRepository.existsByEmail(signUpDTOWithPartner.email())).thenReturn(false);

        authService.isEmailExist(signUpDTOWithPartner.email());

        verify(userRepository, times(1)).existsByEmail(signUpDTOWithPartner.email());
    }
}
