package org.wedding.application.port.in;

import org.wedding.adapter.in.web.dto.LoginDTO;
import org.wedding.adapter.in.web.dto.SignUpDTO;

import jakarta.servlet.http.HttpSession;

public interface AuthUseCase {
    int signUp(SignUpDTO request);
    void login(LoginDTO request, HttpSession session);
}
