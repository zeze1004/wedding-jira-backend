package org.wedding.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.wedding.adapter.in.web.dto.CreateCardRequest;
import org.wedding.application.port.in.usecase.CreateCardUseCase;
import org.wedding.application.port.in.usecase.ModifyCardUseCase;
import org.wedding.common.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name="Card API", description = "Card API")
@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CreateCardUseCase createCardUseCase;
    private final ModifyCardUseCase modifyCardUseCase;

    // 카드 생성 API
    @PostMapping("/card")
    @Operation(summary = "카드 생성", description = "카드 생성")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Void>> createCard(@RequestBody @Valid CreateCardRequest request) {
        createCardUseCase.createCard(request);
        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.CREATED, "카드가 생성되었습니다.");
        return new ResponseEntity<>(response, response.status());
    }

}
