package org.wedding.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.adapter.in.web.dto.SignUpDTO;
import org.wedding.application.port.out.repository.CardBoardRepository;
import org.wedding.application.port.out.repository.UserRepository;
import org.wedding.domain.cardboard.CardBoard;
import org.wedding.domain.user.User;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CreateCardBoardAfterSignUpAPITest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardBoardRepository cardBoardRepository;

    @DisplayName("회원가입시 카드보드에 유저 Id와 빈 카드 Id 리스트를 저장한다.")
    @Test
    void signUp_validRequest_shouldSaveUserAndCardBoard() throws Exception {
        // given
        String email = "test1234@example.com";
        String password = "password1234!!";
        String name = "김오디";
        String nickName = "ODeeS2";
        String partnerEmail = "testPartner@example.com";
        SignUpDTO signUpDTO = new SignUpDTO(email, password, name, nickName, partnerEmail);

        // when
        mockMvc.perform(post("/api/v1/auth/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(signUpDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."));

        // then
        Optional<User> savedUser = userRepository.findByEmail(email);
        assertTrue(savedUser.isPresent());

        int userId = savedUser.get().getId();

        Optional<CardBoard> savedCardBoard = Optional.ofNullable(cardBoardRepository.findCardBoardByUserId(userId));
        assertEquals(userId, savedCardBoard.get().getUserId());
        assertNotNull(savedCardBoard.get().getCardIds());
        assertTrue(savedCardBoard.get().getCardIds().isEmpty());
    }
}
