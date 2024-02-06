package project.wedding.common.response;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import project.wedding.common.exception.CommonError;

@Builder
public record ApiResponse<T>(
    HttpStatus status,
    String message,
    @JsonInclude(value = NON_NULL)
    T body
) {

    public static <T> ApiResponse<T> successApiResponse(HttpStatus status) {
        return ApiResponse.<T>builder()
            .status(status)
            .message("요청이 성공했습니다.")
            .build();
    }

    public static <T> ApiResponse<T> successApiResponse(HttpStatus status, String message) {
        return ApiResponse.<T>builder()
            .status(status)
            .message(message)
            .build();
    }

    public static <T> ApiResponse<T> successApiResponse(HttpStatus status, String message, T body) {
        return ApiResponse.<T>builder()
            .status(status)
            .message(message)
            .body(body)
            .build();
    }

    public static ApiResponse<Void> createApiResponseFromCommonError(CommonError commonError) {
        return ApiResponse.<Void>builder()
            .status(commonError.getHttpStatus())
            .message(commonError.getMessage())
            .build();
    }

    public static ApiResponse<Void> createApiResponseFromCommonError(CommonError commonError, String message) {
        return ApiResponse.<Void>builder()
            .status(commonError.getHttpStatus())
            .message(StringUtils.hasText(message) ? message : commonError.getMessage())
            .build();
    }
}
