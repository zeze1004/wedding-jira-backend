package org.wedding.adapter.in.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.adapter.in.web.dto.ModifyCardRequest;
import org.wedding.common.security.JwtUtils;
import org.wedding.domain.CardStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ModifyCardIntegTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private String token;

    @BeforeEach
    void setUp() {
        int userId = 0; // 테스트용 사용자 ID
        token = JwtUtils.generateToken(userId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userId, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @DisplayName("카드의 모든 인자 수정")
    @Test
    void modifyCardTitle() throws Exception {
        // given
        int cardId = 0; // 테스트용 카드 ID
        CardStatus modifyStatus = CardStatus.DONE;
        ModifyCardRequest modifyCardRequest = ModifyCardRequest.builder()
            .cardTitle(Optional.of("카드 제목 수정"))
            .budget(Optional.of(10000L))
            .cardStatus(Optional.of(modifyStatus))
            .build();

        String request = objectMapper.writeValueAsString(modifyCardRequest);

        // when
        ResultActions resultActions =
            mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/cardboard/{cardId}", cardId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(request));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("카드가 수정되었습니다."));
    }
}
