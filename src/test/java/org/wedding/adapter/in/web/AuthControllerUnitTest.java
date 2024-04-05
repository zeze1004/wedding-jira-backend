package org.wedding.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.wedding.adapter.in.web.dto.LoginDTO;
import org.wedding.adapter.in.web.dto.SignUpDTO;
import org.wedding.adapter.out.dto.TokenResponse;
import org.wedding.application.port.in.AuthUseCase;
import org.wedding.application.service.response.LoginResponse;
import org.wedding.common.response.ApiResponse;
import org.wedding.common.security.JwtUtils;

@ExtendWith(MockitoExtension.class)
class AuthControllerUnitTest {

    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthUseCase authUseCase;
    private SignUpDTO signUpDTO;
    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        signUpDTO = SignUpDTO.builder()
            .email("test@gmail.com")
            .password("password1234!!")
            .name("김오디")
            .nickName("ODeeS2")
            .build();

        loginDTO = new LoginDTO("test@gmail.com", "password1234!!");
    }

    @Test
    void signUp() {

        // given
        when(authUseCase.signUp(signUpDTO)).thenReturn(1);

        // when
        ResponseEntity<ApiResponse<Void>> response = authController.signUp(signUpDTO);

        // then
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("회원가입이 완료되었습니다.", response.getBody().message());
    }

    @Test
    void login() {

        // given
        int userId = 1;
        LoginResponse loginResponse = new LoginResponse(userId);
        when(authUseCase.login(loginDTO)).thenReturn(loginResponse);

        // when
        ResponseEntity<TokenResponse> response = authController.login(loginDTO);
        TokenResponse tokenResponse = response.getBody();

        // then
        assertEquals(userId, loginResponse.userId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(tokenResponse);
        assertEquals(JwtUtils.getUserIdFromToken(tokenResponse.token()), userId);
    }
}
