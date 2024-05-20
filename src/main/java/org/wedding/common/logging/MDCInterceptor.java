package org.wedding.common.logging;

import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MDCInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler) throws Exception {

        final String requestId = UUID.randomUUID().toString();
        final String requestURI = request.getRequestURI();
        final String clientIP = getClientIP(request);

        MDC.put("requestId", requestId);
        MDC.put("requestURI", requestURI);
        MDC.put("clientIp", clientIP);

        if (handler instanceof HandlerMethod handlerMethod) {
            final String methodName = handlerMethod.getMethod().getName();
            final String handlerName = handlerMethod.getBeanType().getSimpleName();
            MDC.put("methodName", methodName);
            MDC.put("handlerName", handlerName);
        }

        return true;
    }

    private String getClientIP(final HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasLength(ip)) {
            ip = ip.split(",")[0].strip();
        } else {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler, final Exception ex) throws Exception {
        MDC.clear();
    }
}
