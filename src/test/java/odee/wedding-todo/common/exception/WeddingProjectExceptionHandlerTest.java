package project.wedding.common.exception;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import org.wedding.common.exception.DomainError;
import org.wedding.common.exception.WeddingProjectError;
import org.wedding.common.exception.WeddingProjectErrorHandlerMap;
import org.wedding.common.exception.WeddingProjectException;
import org.wedding.common.exception.WeddingProjectExceptionHandler;
import org.wedding.common.response.ApiResponse;
import org.wedding.user.exception.UserError;

class WeddingProjectExceptionHandlerTest {

    private WeddingProjectExceptionHandler weddingProjectExceptionHandler;
    private WeddingProjectErrorHandlerMap weddingProjectErrorHandlerMap;

    @BeforeEach
    void setUp() {

        Map<String, DomainError> errorHandlerMap = new HashMap<>();
        errorHandlerMap.put(UserError.class.getName(), DomainError.USER_ERROR);

        weddingProjectErrorHandlerMap = WeddingProjectErrorHandlerMap.builder()
            .errorHandlerMap(errorHandlerMap)
            .build();

        weddingProjectExceptionHandler = new WeddingProjectExceptionHandler(weddingProjectErrorHandlerMap);
    }

    @Test
    void handleRuntimeError() {
        WeddingProjectException weddingProjectException = new WeddingProjectException(WeddingProjectError.INTERNAL_SERVER_ERROR, WeddingProjectError.INTERNAL_SERVER_ERROR.getMessage());

        ResponseEntity<ApiResponse<Void>> response = weddingProjectExceptionHandler.handleException(weddingProjectException);
        assertThat(response.getStatusCode()).isEqualTo(WeddingProjectError.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    @Test
    void handleWeddingProjectException() {
        WeddingProjectException weddingProjectException = new WeddingProjectException(WeddingProjectError.BAD_REQUEST, WeddingProjectError.BAD_REQUEST.getMessage());

        ResponseEntity<ApiResponse<Void>> response = weddingProjectExceptionHandler.handleWeddingProjectException(weddingProjectException);
        assertThat(response.getStatusCode()).isEqualTo(WeddingProjectError.BAD_REQUEST.getHttpStatus());
    }

    @Test
    void handleValidationException() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator javaxValidator = validatorFactory.getValidator();

        SpringValidatorAdapter validatorAdapter = new SpringValidatorAdapter(javaxValidator);

        Person person = new Person("ODEEKIM", 28);

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(person, "person");
        MethodParameter methodParameter = new MethodParameter(Handler.class.getDeclaredMethod("handle", Person.class), -1);

        validatorAdapter.validate(person, bindingResult);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<ApiResponse<Void>> response = weddingProjectExceptionHandler.handleValidationException(ex);
        assertThat(response.getStatusCode()).isEqualTo(WeddingProjectError.BAD_REQUEST.getHttpStatus());
    }


    @SuppressWarnings("unused")
    private static class Handler {

        @SuppressWarnings("unused")
        void handle(Person person) {
        }
    }

    @SuppressWarnings("unused")
    public record Person(
        @Size(max = 3, payload = UserError.class)
        String name,
        @Min(value = 25, payload = UserError.class)
        int age
    ) {}
}
