package project.wedding.common.logging;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class MDCInterceptorTest {

    @InjectMocks
    private MDCInterceptor mdcInterceptor;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private final Object handler = new Object();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void preHandle_SetMDCContext() throws Exception {
        // given
        request.setRequestURI("/test/uri");
        request.addHeader("X-Forwarded-For", "172.0.0.10");

        assertTrue(mdcInterceptor.preHandle(request, response, handler));
        assertNotNull(MDC.get("requestId"));
        assertEquals("/test/uri", MDC.get("requestURI"));
        assertEquals("172.0.0.10", MDC.get("clientIp"));
    }

    @Test
    void afterCompletion_ClearMDCContext() throws Exception {
        MDC.put("requestId", UUID.randomUUID().toString());
        MDC.put("requestURI", "/test/uri");
        MDC.put("clientIp", "172.0.0.10");

        mdcInterceptor.afterCompletion(request, response, handler, null);

        assertNull(MDC.get("requestId"));
        assertNull(MDC.get("requestURI"));
        assertNull(MDC.get("clientIp"));
    }
}
