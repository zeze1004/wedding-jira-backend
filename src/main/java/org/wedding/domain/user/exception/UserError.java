package org.wedding.domain.user.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wedding.common.exception.CommonError;

@Getter
@AllArgsConstructor
public enum UserError implements CommonError {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),
    EMAIL_FORMAT_NOT_VALID(HttpStatus.BAD_REQUEST, "이메일 형식이 유효하지 않습니다."),
    PARTNER_EMAIL_FORMAT_NOT_VALID(HttpStatus.BAD_REQUEST, "배우자 이메일 형식이 유효하지 않습니다."),
    EMAIL_IS_REQUIRED(HttpStatus.BAD_REQUEST, "이메일을 입력해주세요."),
    NAME_IS_REQUIRED(HttpStatus.BAD_REQUEST, "이름을 입력해주세요."),
    NAME_SIZE_NOT_VALID(HttpStatus.BAD_REQUEST, "이름은 2자 이상 7자 이하로 입력해주세요."),
    NICKNAME_IS_REQUIRED(HttpStatus.BAD_REQUEST, "애칭을 입력해주세요."),
    NICKNAME_SIZE_NOT_VALID(HttpStatus.BAD_REQUEST, "애칭은 2자 이상 10자 이하로 입력해주세요."),
    PASSWORD_IS_REQUIRED(HttpStatus.BAD_REQUEST, "비밀번호를 입력해주세요."),
    PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호가 유효하지 않습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    PARTNER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일을 가진 예비 배우자를 찾을 수 없습니다."),
    PARTNER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 예비 배우자가 존재합니다."),
    PARTNER_EXIST_IN_OTHER_PARTNER(HttpStatus.BAD_REQUEST, "해당 이메일을 가진 예비 배우자는 다른 예비 배우자와 연결되어 있습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public static UserError of(String error) {
        return Arrays.stream(UserError.values())
            .filter(userError -> userError.name().equalsIgnoreCase(error))
            .findAny()
            .orElse(null);
    }
}
