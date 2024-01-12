package project.wedding.api.controller.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import project.wedding.api.service.card.CreateCardService;

@RestController
@Tag(name = "Card", description = "Card API")
public class CardApiController {

    private final CreateCardService createCardService;

    public CardApiController(CreateCardService createCardService) {
        this.createCardService = createCardService;
    }

    @PostMapping("api/v1/card")
    @Operation(summary = "create card", description = "카드 생성")
    public void createCard(String userId) {
        createCardService.createCard(userId);
        // todo: 카드 만든 이 저장
    }


}
