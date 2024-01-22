package project.wedding.user.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.wedding.common.exception.CommonError;

@Getter
@AllArgsConstructor
public enum UserError implements CommonError {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호가 유효하지 않습니다."),
    PARTNER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일을 가진 예비 배우자를 찾을 수 없습니다."),
    PARTNER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 예비 배우자가 존재합니다."),
    PARTNER_EXIST_IN_OTHER_PARTNER(HttpStatus.BAD_REQUEST, "해당 이메일을 가진 예비 배우자는 다른 예비 배우자와 연결되어 있습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public static UserError of(String errorMessage) {
        return Arrays.stream(UserError.values())
            .filter(userError -> userError.name().equalsIgnoreCase(errorMessage))
            .findAny()
            .orElse(null);
    }
}
