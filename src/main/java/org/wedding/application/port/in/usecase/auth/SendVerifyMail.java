package org.wedding.application.port.in.usecase.auth;

public interface SendVerifyMail {

    void sendVerificationCode(String email);
}
