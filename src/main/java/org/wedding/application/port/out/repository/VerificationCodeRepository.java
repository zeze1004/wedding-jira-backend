package org.wedding.application.port.out.repository;

import org.wedding.domain.VerificationCode;

public interface VerificationCodeRepository {
    void insertVerificationCode(VerificationCode verificationCode);

    String selectVerificationCode(String email);
}
