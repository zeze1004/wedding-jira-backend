package org.wedding.adapter.in.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.wedding.adapter.out.dto.CardsResponse;
import org.wedding.application.port.in.usecase.cardboard.CardBoardUseCase;
import org.wedding.application.port.in.command.cardboard.ReadCardCommand;
import org.wedding.application.service.response.cardboard.CardInfo;
import org.wedding.domain.CardStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name="CardBoard API", description = "유저와 카드를 연결하는 카드보드 API")
@RestController
@RequestMapping("/api/v1/cardboard")
@RequiredArgsConstructor
public class CardBoardController {

    private final CardBoardUseCase cardBoardUseCase;

    @GetMapping("/{cardStatus}")
    @Operation(summary = "카드 상태별 조회" , description = "카드 상태별 조회")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CardsResponse>> readCardsByStatus(@PathVariable CardStatus cardStatus) {
        int userId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ReadCardCommand command = new ReadCardCommand(userId, cardStatus);
        List<CardInfo> cardInfos = cardBoardUseCase.readCardsByStatus(command);
        List<CardsResponse> response = new ArrayList<>();
        for (CardInfo cardInfo : cardInfos) {
            response.add(
                new CardsResponse(
                    cardInfo.cardId(),
                    cardInfo.cardTitle(),
                    cardInfo.budget(),
                    cardInfo.deadline(),
                    cardInfo.cardStatus()
                ));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
