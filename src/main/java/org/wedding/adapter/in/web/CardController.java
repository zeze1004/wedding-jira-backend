package org.wedding.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.wedding.adapter.in.web.dto.CreateCardRequest;
import org.wedding.adapter.in.web.dto.ModifyCardRequest;
import org.wedding.application.port.in.command.card.CreateCardCommand;
import org.wedding.application.port.in.command.card.ModifyCardCommand;
import org.wedding.application.port.in.usecase.card.CreateCardUseCase;
import org.wedding.application.port.in.usecase.card.DeleteCardUseCase;
import org.wedding.application.port.in.usecase.card.ModifyCardUseCase;
import org.wedding.application.port.in.usecase.card.ReadCardUseCase;
import org.wedding.application.service.response.card.ReadCardResponse;
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
    private final ReadCardUseCase   readCardUseCase;
    private final DeleteCardUseCase deleteCardUseCase;

    @PostMapping()
    @Operation(summary = "카드 생성", description = "카드 생성")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Void>> createCard(@RequestBody @Valid CreateCardRequest cardRequest) {

        int userId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CreateCardCommand command = new CreateCardCommand(userId, cardRequest.cardTitle(), cardRequest.budget(), cardRequest.deadline());

        createCardUseCase.createCard(command);

        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.CREATED, "카드가 생성되었습니다.");
        return new ResponseEntity<>(response, response.status());
    }

    @PatchMapping("/{cardId}")
    @Operation(summary = "카드 수정", description = "카드 수정")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Void>> modifyCard(
        @PathVariable int cardId,
        @RequestBody @Valid ModifyCardRequest request) {

        ModifyCardCommand command = new ModifyCardCommand(request.cardTitle(), request.budget(), request.deadline(), request.cardStatus());

        modifyCardUseCase.modifyCard(cardId, command);

        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.OK, "카드가 수정되었습니다.");
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/id/{cardId}")
    @Operation(summary = "카드 ID로 조회", description = "카드 ID로 조회")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReadCardResponse> readCardByCardId(@PathVariable int cardId) {

        ReadCardResponse response = readCardUseCase.readCardByCardId(cardId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/title/{cardTitle}")
    @Operation(summary = "카드 제목으로 조회", description = "카드 제목으로 조회")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReadCardResponse> readCardByCardTitle(@PathVariable String cardTitle) {

        ReadCardResponse response = readCardUseCase.readCardsByCardTitle(cardTitle);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{cardId}")
    @Operation(summary = "카드 삭제", description = "카드 삭제")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Void>> deleteCard(@PathVariable int cardId) {

        deleteCardUseCase.deleteCard(cardId);

        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.OK, "카드가 삭제되었습니다.");
        return new ResponseEntity<>(response, response.status());
    }
}
