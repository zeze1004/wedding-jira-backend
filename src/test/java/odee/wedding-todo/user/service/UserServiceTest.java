package project.wedding.user.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.wedding.user.domain.User;
import org.wedding.user.repository.UserRepository;
import org.wedding.user.repository.mybatis.dto.request.SignUpRequest;
import org.wedding.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;

    private SignUpRequest signUpRequestWithPartner;
    private SignUpRequest signUpRequestWithoutPartner;

    @BeforeEach
    void setUp() {
        signUpRequestWithPartner = new SignUpRequest(
            "test1@gmail.com",
            "password1234",
            "김커플",
            "ODeeS2",
            "testPartner@gmail.com"
        );
        signUpRequestWithoutPartner = new SignUpRequest(
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
        when(userRepository.existsByEmail(signUpRequestWithPartner.email())).thenReturn(false);

        userService.signUp(signUpRequestWithPartner);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("회원가입 성공 - 예비 배우자 이메일을 안 넣은 경우")
    @Test
    void signUpWithoutPartnerEmail() {
        when(userRepository.existsByEmail(signUpRequestWithoutPartner.email())).thenReturn(false);

        userService.signUp(signUpRequestWithoutPartner);

        verify(userRepository, times(1)).save(any(User.class));
    }
}
