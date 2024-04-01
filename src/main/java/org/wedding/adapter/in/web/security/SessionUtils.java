package org.wedding.adapter.in.web.security;

import java.util.Optional;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.wedding.domain.user.exception.UserError;
import org.wedding.domain.user.exception.UserException;

public class SessionUtils {

    public static int getCurrentUserId() {

        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
            .map(attributes -> ((ServletRequestAttributes)attributes).getRequest().getSession(false))
            .map(session -> session.getAttribute("userId"))
            .map(userId -> (int)userId)
            .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
    }
}
