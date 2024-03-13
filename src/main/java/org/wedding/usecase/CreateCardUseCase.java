package org.wedding.usecase;

import java.util.logging.Logger;

import org.wedding.domain.CardContainerRepository;
import org.wedding.domain.CardBoard;

public class CreateCardUseCase {
    private final CardContainerRepository cardContainerRepository;
    private final Logger logger = Logger.getLogger(CreateCardUseCase.class.getName());

    public CreateCardUseCase(CardContainerRepository cardContainerRepository) {
        this.cardContainerRepository = cardContainerRepository;
    }

    public void createCard(String userId) {
        CardBoard container = cardContainerRepository.findByUserId(userId);
        container.createCard();
        logger.info("ID: " + userId + "의 카드가 생성됐습니다.");
    }
}
