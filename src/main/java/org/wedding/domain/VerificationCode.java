package org.wedding.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerificationCode {
    private final String email;
    private final String verificationCode;
}
