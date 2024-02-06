package project.wedding.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.wedding.common.response.ApiResponse;
import project.wedding.user.repository.mybatis.dto.request.SignUpRequest;
import project.wedding.user.service.UserService;

@Slf4j
@Validated
@Tag(name="User API", description = "User API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody @Valid SignUpRequest request) {
        userService.signUp(request);
        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.CREATED, "회원가입이 완료되었습니다.");
        return new ResponseEntity<>(response, response.status());
    }

}
