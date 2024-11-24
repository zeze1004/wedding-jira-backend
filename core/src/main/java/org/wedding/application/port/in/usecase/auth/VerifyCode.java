package org.wedding.application.port.in.usecase.auth;

import org.springframework.cache.Cache;

public interface VerifyCode {

    Cache getVerificationCache(String cacheName);

    void verifyCode(String email, String code);

    void verififyUser(String email);

    void saveVerifiedUser(String email);

    void deleteCode(String email);
}
