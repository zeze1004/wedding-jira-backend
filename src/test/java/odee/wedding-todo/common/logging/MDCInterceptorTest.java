package project.wedding.common.logging;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.wedding.common.logging.MDCInterceptor;

class MDCInterceptorTest {

    @InjectMocks
    private MDCInterceptor mdcInterceptor;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    Method method = TestController.class.getMethod("testMethod");
    HandlerMethod handler = new HandlerMethod(new TestController(), method);

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
        assertEquals("testMethod", MDC.get("methodName"));
        assertEquals("TestController", MDC.get("handlerName"));
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

    static class TestController {
        public void testMethod() {}
    }

    MDCInterceptorTest() throws NoSuchMethodException {
    }
}
