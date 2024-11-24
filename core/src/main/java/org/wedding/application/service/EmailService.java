package org.wedding.application.service;

import java.util.Objects;
import java.util.Random;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.wedding.application.port.in.usecase.auth.CheckExistMail;
import org.wedding.application.port.in.usecase.auth.CreateVerificationCode;
import org.wedding.application.port.in.usecase.auth.SendVerifyMail;
import org.wedding.application.port.in.usecase.auth.VerifyCode;
import org.wedding.domain.user.exception.UserError;
import org.wedding.domain.user.exception.UserException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmailService implements SendVerifyMail, CreateVerificationCode, VerifyCode {

    private final JavaMailSender mailSender;
    private final CacheManager cacheManager;
    private final CheckExistMail checkExistMail;

    @Override
    public void sendVerificationCode(String email) {

        checkExistMail.isEmailExist(email);

        String verificationCode = generateVerificationCode();
        Cache cache = getVerificationCache("verificationCode");
        Objects.requireNonNull(cache).put(email, verificationCode);

        String subject = "웨딩 지라 회원가입 인증 메일입니다.";
        String message =
            "메일 인증을 위해 인증 번호 <"
                + verificationCode +
                "> 를 회원가입 페이지에 입력해주세요.\n";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new UserException(UserError.EMAIL_SEND_FAILED);
        }
    }

    @Override
    public Cache getVerificationCache(String cacheName) {
        return cacheManager.getCache(cacheName);
    }

    @Override
    public void verifyCode(String email, String code) {
        Cache cache = getVerificationCache("verificationCode");
        String verificationCode = Objects.requireNonNull(cache.get(email)).toString().substring(18, 24);

        if (!code.equals(verificationCode)) {
            throw new UserException(UserError.EMAIL_VERIFICATION_CODE_IS_INVALID);
        }
    }

    @Override
    public void verififyUser(String email) {
        Cache cache = getVerificationCache("verifiedEmails");
        if (Objects.requireNonNull(cache).get(email) == null) {
            throw new UserException(UserError.EMAIL_IS_NOT_VERIFIED);
        }
    }

    @Override
    public void saveVerifiedUser(String email) {
        Cache cache = getVerificationCache("verifiedEmails");
        Objects.requireNonNull(cache).put(email, true);
    }

    @Override
    public void deleteCode(String email) {
        Cache cache = getVerificationCache("verificationCode");
        Objects.requireNonNull(cache).evict(email);
    }

    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
