package org.wedding.config;

import static org.wedding.common.exception.DomainError.*;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wedding.domain.user.exception.UserError;

import lombok.RequiredArgsConstructor;
import org.wedding.common.exception.DomainError;
import org.wedding.common.exception.WeddingProjectErrorHandlerMap;

@Configuration
@RequiredArgsConstructor
public class ErrorHandlerMapConfig {

    @Bean
    public WeddingProjectErrorHandlerMap errorHandlerMap() {
        Map<String, DomainError> errorHandlerMap = Map.of(
            UserError.class.getName(), USER_ERROR
        );

        return WeddingProjectErrorHandlerMap.builder()
            .errorHandlerMap(errorHandlerMap)
            .build();
    }
}
