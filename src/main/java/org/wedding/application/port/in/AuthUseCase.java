package org.wedding.application.port.in;

import org.wedding.adapter.in.web.dto.LoginDTO;
import org.wedding.adapter.in.web.dto.SignUpDTO;
import org.wedding.application.service.response.LoginResponse;

public interface AuthUseCase {

    int signUp(SignUpDTO request);

    LoginResponse login(LoginDTO request);
}
