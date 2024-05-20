package org.wedding.common.exception;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.wedding.common.response.ApiResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Payload;
import jakarta.validation.metadata.ConstraintDescriptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class WeddingProjectExceptionHandler {

    private final WeddingProjectErrorHandlerMap errorHandlerMap;

    private static void logging(Exception ex, ApiResponse<Void> response) {
        log.error("{}", response.message(), ex);
    }

    /**
     * ExceptionHandler
     *
     * @param ex WeddingProjectException
     * @return ResponseEntity<ApiResponse < Void>>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
            .status(WeddingProjectError.INTERNAL_SERVER_ERROR.getHttpStatus())
            .message(WeddingProjectError.INTERNAL_SERVER_ERROR.getMessage())
            .build();
        logging(ex, response);
        return new ResponseEntity<>(response, response.status());
    }

    /**
     * WeddingProjectException 서비스 단에서 지정된 예외처리
     *
     * @param ex WeddingProjectException
     * @return ResponseEntity<ApiResponse < Void>>
     */
    @ExceptionHandler(WeddingProjectException.class)
    public ResponseEntity<ApiResponse<Void>> handleWeddingProjectException(WeddingProjectException ex) {
        String message = String.format("%s",
            ObjectUtils.isEmpty(ex.getMessage()) ? ex.getCommonError().getMessage() : ex.getMessage());
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ex.getCommonError(), message);
        return new ResponseEntity<>(response, response.status());
    }

    /**
     * handleValidationException
     *
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity<ApiResponse < Void>>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        CommonError commonError = null;

        // 발생한 필드 오류를 순회하며 Payload 서브 클래스를 사용해 에러 처리
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            // 필드 오류와 연관된 Payload 클래스들을 가져옴
            Set<Class<? extends Payload>> payloads = extractPayloadsFromFieldError(fieldError);
            if (!payloads.isEmpty()) {
                Class<? extends Payload> payload = payloads.iterator().next();
                commonError = errorHandlerMap.getErrorHandlerMap().get(payload.getName())
                    .of(fieldError.getDefaultMessage());
                break; // Payload 클래스가 여러 개일 경우 첫 번째 Payload 클래스만 사용
            }
        }

        ApiResponse<Void> response;

        if (commonError == null) { // Payload 클래스가 없을 경우 기본 에러 처리
            String message = String.format("[MethodArgumentNotValidException] %s",
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            response = ApiResponse.createApiResponseFromCommonError(WeddingProjectError.BAD_REQUEST, message);
        } else {
            response = ApiResponse.createApiResponseFromCommonError(commonError);
        }

        return new ResponseEntity<>(response, response.status());
    }

    /**
     * FieldError에서 Payload 클래스를 추출
     *
     * @param error FieldError
     * @return Set<Class < ? extends Payload>>
     */
    private Set<Class<? extends Payload>> extractPayloadsFromFieldError(FieldError error) {
        ConstraintDescriptor<?> constraintDescriptor = error.unwrap(ConstraintViolation.class)
            .getConstraintDescriptor();
        return constraintDescriptor.getPayload();
    }
}
