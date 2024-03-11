package project.wedding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import project.wedding.common.logging.MDCInterceptor;

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
