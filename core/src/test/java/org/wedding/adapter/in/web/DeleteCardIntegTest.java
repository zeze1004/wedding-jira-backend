package org.wedding.adapter.in.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

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
import org.wedding.adapter.in.web.dto.DeleteCardRequest;
import org.wedding.common.security.JwtUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DeleteCardIntegTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private String token;

    @BeforeEach
    void setUp() {
        int userId = 0; // 테스트용 사용자 ID
        token = JwtUtils.generateToken(userId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken (
            userId, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @DisplayName("카드 삭제 성공")
    @Test
    void deleteCardSuccess() throws Exception {
        // given
        int cardId = 0; // 테스트용 카드 ID
        DeleteCardRequest deleteCardRequest = new DeleteCardRequest(cardId);
        String request = objectMapper.writeValueAsString(deleteCardRequest);

        // when
        ResultActions resultActions =
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cardboard/{cardId}", cardId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(request));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("카드가 삭제되었습니다."));
    }

    @DisplayName("카드 삭제 실패")
    @Test
    void deleteCardFail() throws Exception {
        // given
        int cardId = -1; // 존재하지 않는 카드ID
        DeleteCardRequest deleteCardRequest = new DeleteCardRequest(cardId);
        String request = objectMapper.writeValueAsString(deleteCardRequest);

        // when
        ResultActions resultActions =
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cardboard/{cardId}", cardId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(request));

        // then
        resultActions.andExpect(status().isForbidden())
            .andExpect(jsonPath("$.message").value("카드 소유자가 일치하지 않습니다."));
    }
}
