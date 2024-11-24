package org.wedding.common.exception;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeddingProjectErrorHandlerMap {

    private Map<String, DomainError> errorHandlerMap;
}
