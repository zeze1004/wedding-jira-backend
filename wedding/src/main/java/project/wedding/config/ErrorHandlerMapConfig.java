package project.wedding.config;

import static project.wedding.common.exception.DomainError.*;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import project.wedding.common.exception.DomainError;
import project.wedding.common.exception.WeddingProjectErrorHandlerMap;
import project.wedding.user.exception.UserError;

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
