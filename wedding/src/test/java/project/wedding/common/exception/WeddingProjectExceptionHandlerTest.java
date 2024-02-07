package project.wedding.common.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static project.wedding.common.exception.WeddingProjectError.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@WebMvcTest(WeddingProjectExceptionHandler.class)
class WeddingProjectExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private TestController testController;
    @MockBean
    private WeddingProjectErrorHandlerMap weddingProjectErrorHandlerMap;

    private static final StaticApplicationContext applicationContext = new StaticApplicationContext();
    private static final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();

    @BeforeEach
    void setUp() {
        applicationContext.registerSingleton("WeddingProjectExceptionHandler", WeddingProjectExceptionHandler.class);
        webMvcConfigurationSupport.setApplicationContext(applicationContext);

        mockMvc = standaloneSetup(testController)
            .setControllerAdvice(new WeddingProjectExceptionHandler(weddingProjectErrorHandlerMap))
            .build();
    }

    @RestController
    private static class TestController {

        @GetMapping("/test/{exception}")
        String show(@PathVariable String exception) {
            return switch (exception) {
                case "BAD_REQUEST" -> throw new WeddingProjectException(BAD_REQUEST);
                case "METHOD_NOT_ALLOWED" -> throw new WeddingProjectException(METHOD_NOT_ALLOWED);
                case "INTERNAL_SERVER_ERROR" -> throw new WeddingProjectException(INTERNAL_SERVER_ERROR);
                case "NOT_FOUND" -> throw new WeddingProjectException(NOT_FOUND);

                default -> HttpStatus.OK.getReasonPhrase();
            };
        }
    }

    @Nested
    class RestTests {
        @Test
        void noException() throws Exception {

            mockMvc.perform(get("/test/ok").accept("application/json"))
                .andExpect(status().isOk());
        }

        @Test
        void badRequestException() throws Exception {
            mockMvc.perform(get("/test/BAD_REQUEST").accept("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(BAD_REQUEST.getMessage()));
        }

        @Test
        void methodNotAllowedException() throws Exception {
            mockMvc.perform(get("/test/METHOD_NOT_ALLOWED").accept("application/json"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value(METHOD_NOT_ALLOWED.getMessage()));
        }

        @Test
        void ServerException() throws Exception {
            mockMvc.perform(get("/test/INTERNAL_SERVER_ERROR").accept("application/json"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(INTERNAL_SERVER_ERROR.getMessage()));
        }

        @Test
        void notFoundException() throws Exception {
            mockMvc.perform(get("/test/NOT_FOUND").accept("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(NOT_FOUND.getMessage()));
        }

        @Test
        void iligalArgumentException() throws Exception {
            mockMvc.perform(get("/test").accept("application/json"))
                .andExpect(status().isNotFound());
        }
    }
}
