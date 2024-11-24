package org.wedding.application.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.adapter.in.web.dto.SignUpDTO;
import org.wedding.application.port.out.repository.CardBoardRepository;
import org.wedding.domain.cardboard.CardBoard;

@Transactional
@SpringBootTest
public class CreaetCardBoardAfterSignUpServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private CardBoardRepository cardBoardRepository;

    private SignUpDTO signUpDTO;

    @BeforeEach
    void setUp() {
        signUpDTO = new SignUpDTO(
            "test1234@example.com",
            "password1234!!",
            "김오디",
            "ODeeS2");
    }

    @DisplayName("회원가입 서비스 로직에 DTO가 전달될 때 카드보드 생성 이벤트가 실행되어 카드보드 객체에 userId가 담긴다")
    @Test
    void userCreatedEvent_shouldCreateCardBoard() {
        int userId = authService.signUp(signUpDTO);

        CardBoard cardBoard = cardBoardRepository.findCardBoardByUserId(userId);
        assertEquals(userId, cardBoard.getUserId());
    }
}
