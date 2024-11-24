package org.wedding.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.common.security.JwtUtils;
import org.wedding.domain.CardStatus;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ReadCardBoardIntegTest {

    @Autowired
    private MockMvc mockMvc;
    private String token;

    @BeforeEach
    void setUp() {
        int userId = 0; // 테스트용 사용자 ID
        token = JwtUtils.generateToken(userId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken (
            userId, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void readCardsByStatus() throws Exception {
        // given
        CardStatus cardStatus = CardStatus.BACKLOG;

        // when
        MockHttpServletRequestBuilder request =
            get("/api/v1/cardboard/{cardStatus}", cardStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token);

        // then
        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].cardId").isNumber())
            .andExpect(jsonPath("$[0].cardTitle").isString())
            .andExpect(jsonPath("$[0].cardStatus").isString());
    }
}

