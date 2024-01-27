package project.wedding.user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static project.wedding.user.exception.UserError.*;

import org.junit.jupiter.api.BeforeEach;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import project.wedding.user.exception.UserException;
import project.wedding.user.repository.mybatis.dto.request.SignUpRequest;
import project.wedding.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .alwaysDo(print())
            .build();
    }

    @DisplayName("회원가입 실패 - 유효하지 않은 이메일 주소인 경우")
    @Test
    void failSignUpWithInvalidEmail() throws Exception {
        SignUpRequest signUpRequestWithInvalidEmail = SignUpRequest.builder()
            .email("test")
            .password("password1234!!")
            .name("김커플")
            .nickName("ODeeS2")
            .partnerEmail("testPartner@gmail.com")
            .build();
        mockMvc.perform(post("/api/v1/users/sign-up")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequestWithInvalidEmail)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(EMAIL_FORMAT_NOT_VALID.getMessage()))
            .andReturn();
    }

    @DisplayName("회원가입 실패 - 유효하지 않은 비밀번호인 경우")
    @Test
    void failSignUpWithInvalidPassword() throws Exception {
        SignUpRequest signUpRequestWithInvalidPassword = SignUpRequest.builder()
            .email("test@gmail.com")
            .password("1234") // 영문, 숫자, 특수문자 조합 8자리 이상
            .name("김커플")
            .nickName("ODeeS2")
            .build();
        mockMvc.perform(post("/api/v1/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequestWithInvalidPassword)))
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
        SignUpRequest signUpRequest = SignUpRequest.builder()
            .email("duplicate@gmail.com")
            .password("password1234!!")
            .name("김커플")
            .nickName("ODeeS2")
            .build();

        // when
        doThrow(new UserException(EMAIL_DUPLICATION))
            .when(userService).signUp(signUpRequest);
        ResultActions resultActions = mockMvc.perform(post("/api/v1/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequest)));

        // then
        resultActions
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("[WeddingProjectException] " + EMAIL_DUPLICATION.getMessage()))
            .andReturn();
    }

    @DisplayName("회원가입 성공")
    @Test
    void successSignUp() throws Exception {
        // given
        SignUpRequest signUpRequest = SignUpRequest.builder()
            .email("test@gmail.com")
            .password("password1234!!")
            .name("김커플")
            .nickName("ODeeS2")
            .build();

        // when
        mockMvc.perform(post("/api/v1/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequest)))
        .andExpect(status().isCreated());

        // then
        verify(userService, times(1)).signUp(signUpRequest);
    }
}
