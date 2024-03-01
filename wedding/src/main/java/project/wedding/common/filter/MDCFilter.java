package project.wedding.common.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

// @Component
public class MDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            MDC.put("requestId", getRequestId());
            MDC.put("requestURI", httpServletRequest.getRequestURI());

            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private String getRequestId() {
        return UUID.randomUUID().toString();
    }
}
