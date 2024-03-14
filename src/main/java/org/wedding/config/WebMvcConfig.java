package org.wedding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.wedding.common.logging.MDCInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final MDCInterceptor mdcInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(mdcInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/swagger-ui.html")
            .order(1);

        // TODO: Add other interceptors
    }

    // TODO: Add cors handling before connecting front
}
