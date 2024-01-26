package project.wedding.common.response;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Builder;
import project.wedding.common.exception.CommonError;

@Builder
public record ApiResponse<T>(
    @JsonIgnore
    HttpStatus status,
    String message,
    @JsonUnwrapped
    @JsonInclude(value = NON_NULL)
    T body
) {

    public static ApiResponse<Void> createEmptyApiResponse() {
        return ApiResponse.<Void>builder()
            .status(HttpStatus.OK)
            .message("SUCCESS")
            .build();
    }

    public static <T> ApiResponse<T> createApiResponse(T body) {
        return ApiResponse.<T>builder()
            .status(HttpStatus.OK)
            .message("SUCCESS")
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
