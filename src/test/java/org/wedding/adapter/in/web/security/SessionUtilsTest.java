package org.wedding.adapter.in.web.security;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.wedding.domain.user.exception.UserException;

class SessionUtilsTest {

    private MockHttpSession session;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {

        session = new MockHttpSession();
        request = new MockHttpServletRequest();
        request.setSession(session);
    }

    @Test
    void getCurrentUserId_WhenSuccess() {

        // given
        int expectedUserId = 1;
        session.setAttribute("userId", expectedUserId);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // when
        int actualUserId = SessionUtils.getCurrentUserId();

        // then
        assertThat(actualUserId).isEqualTo(expectedUserId);
    }

    @Test
    void getCurrentUserId_WhenSessionIsNull() {

        // given: 세션 설정 하지 않음
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request, null));

        // then
        assertThrows(UserException.class, SessionUtils::getCurrentUserId);
    }

    @Test
    void getCurrentUserId_WhenUserIdIsNull() {

        // given
        session.setAttribute("userId", null);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // then
        assertThrows(UserException.class, SessionUtils::getCurrentUserId);
    }
}
