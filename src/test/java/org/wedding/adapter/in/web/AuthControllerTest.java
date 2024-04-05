package org.wedding.adapter.in.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.wedding.domain.user.exception.UserError.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.wedding.adapter.in.web.dto.SignUpDTO;
import org.wedding.application.port.in.AuthUseCase;
import org.wedding.domain.user.exception.UserException;

import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthUseCase authUseCase;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .alwaysDo(print())
            .build();
    }

    @DisplayName("회원가입 실패 - 유효하지 않은 이메일 주소인 경우")
    @Test
    void failSignUpWithInvalidEmail() throws Exception {
        SignUpDTO signUpDTOWithInvalidEmail = SignUpDTO.builder()
            .email("test")
            .password("password1234!!")
            .name("김커플")
            .nickName("ODeeS2")
            .partnerEmail("testPartner@gmail.com")
            .build();
        mockMvc.perform(post("/api/v1/auth/sign-up")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpDTOWithInvalidEmail)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(EMAIL_FORMAT_NOT_VALID.getMessage()))
            .andReturn();
    }

    @DisplayName("회원가입 실패 - 유효하지 않은 비밀번호인 경우")
    @Test
    void failSignUpWithInvalidPassword() throws Exception {
        SignUpDTO signUpDTOWithInvalidPassword = SignUpDTO.builder()
            .email("test@gmail.com")
            .password("1234") // 영문, 숫자, 특수문자 조합 8자리 이상
            .name("김커플")
            .nickName("ODeeS2")
            .build();
        mockMvc.perform(post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDTOWithInvalidPassword)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(PASSWORD_NOT_VALID.getMessage()))
            .andReturn();
    }

    // 이메일 중복은 @Valid로 처리하지 않고, 서비스에서 GlobalExceptionHandler를 통해 처리함
    @DisplayName("회원가입 실패 - 중복된 메일인 경우")
    @Test
    void failSignUpWithDuplicatedEmail() throws Exception {
        // given
        SignUpDTO signUpDTO = SignUpDTO.builder()
            .email("duplicate@gmail.com")
            .password("password1234!!")
            .name("김커플")
            .nickName("ODeeS2")
            .build();

        // when
        doThrow(new UserException(EMAIL_DUPLICATION))
            .when(authUseCase).signUp(signUpDTO);
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpDTO)));

        // then
        resultActions
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(EMAIL_DUPLICATION.getMessage()))
            .andReturn();
    }

    @DisplayName("회원가입 성공")
    @Test
    void successSignUp() throws Exception {
        // given
        SignUpDTO signUpDTO = SignUpDTO.builder()
            .email("test@gmail.com")
            .password("password1234!!")
            .name("김커플")
            .nickName("ODeeS2")
            .build();

        // when
        mockMvc.perform(post("/api/v1/auth/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpDTO)))
        .andExpect(status().isCreated());

        // then
        verify(authUseCase, times(1)).signUp(signUpDTO);
    }
}
