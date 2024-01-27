package project.wedding.card.service;

import project.wedding.cardboard.domain.CardBoard;
import project.wedding.cardboard.repository.CardContainerRepository;

public class CreateCardService {
    private final CardContainerRepository cardContainerRepository;

    public CreateCardService(CardContainerRepository cardContainerRepository) {
        this.cardContainerRepository = cardContainerRepository;
    }

    public void createCard(int userId) {
        CardBoard container = cardContainerRepository.findByUserId(userId);
        container.createCard();
    }
}
