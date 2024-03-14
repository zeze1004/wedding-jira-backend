package org.wedding.user.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.wedding.application.service.UserService;
import org.wedding.domain.user.User;
import org.wedding.application.port.out.repository.UserRepository;
import org.wedding.adapter.in.web.dto.SignUpDTO;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;

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
        userService = new UserService(userRepository);
    }

    @DisplayName("회원가입 성공 - 예비 배우자 이메일을 넣은 경우")
    @Test
    void signUpWithPartnerEmail() {
        when(userRepository.existsByEmail(signUpDTOWithPartner.email())).thenReturn(false);

        userService.signUp(signUpDTOWithPartner);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("회원가입 성공 - 예비 배우자 이메일을 안 넣은 경우")
    @Test
    void signUpWithoutPartnerEmail() {
        when(userRepository.existsByEmail(signUpDTOWithoutPartner.email())).thenReturn(false);

        userService.signUp(signUpDTOWithoutPartner);

        verify(userRepository, times(1)).save(any(User.class));
    }
}
