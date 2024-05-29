package org.wedding.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.wedding.adapter.in.web.dto.LoginDTO;
import org.wedding.adapter.in.web.dto.SendMailRequest;
import org.wedding.adapter.in.web.dto.SignUpDTO;
import org.wedding.adapter.in.web.dto.VerifyMailRequest;
import org.wedding.adapter.out.dto.TokenResponse;
import org.wedding.application.port.in.usecase.auth.AuthUseCase;
import org.wedding.application.port.in.usecase.auth.SendVerifyMail;
import org.wedding.application.port.in.usecase.auth.VerifyCode;
import org.wedding.application.service.response.LoginResponse;
import org.wedding.common.response.ApiResponse;
import org.wedding.common.security.JwtUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Auth API", description = "Auth API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;
    private final SendVerifyMail sendMail;
    private final VerifyCode verifyCode;

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody @Valid SignUpDTO request) {

        verifyCode.verififyUser(request.email());
        authUseCase.signUp(request);
        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.CREATED, "회원가입이 완료되었습니다.");
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginDTO loginRequest) {

        LoginResponse loginResponse = authUseCase.login(loginRequest);

        String token = JwtUtils.generateToken(loginResponse.userId());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/send-verify-mail")
    @Operation(summary = "이메일 인증 코드 전송", description = "유저 이메일 중복 검사 후 인증 코드 전송")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Void>> sendEmail(@RequestBody @Valid SendMailRequest request) {

        sendMail.sendVerificationCode(request.email());
        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.OK, "이메일로 인증 코드를 전송했습니다.");

        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/verify-code")
    @Operation(summary = "이메일 인증 코드 검증", description = "이메일과 인증 코드 쌍이 옳바른지 검증")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(@RequestBody @Valid VerifyMailRequest request) {

        verifyCode.verifyCode(request.email(), request.verificationCode());
        verifyCode.deleteCode(request.email());
        verifyCode.saveVerifiedUser(request.email());

        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.OK, "이메일 인증이 완료되었습니다.");
        return new ResponseEntity<>(response, response.status());
    }
}
