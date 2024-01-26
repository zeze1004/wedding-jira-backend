package project.wedding.card.usecase;

import project.wedding.cardboard.domain.CardBoard;
import project.wedding.cardboard.repository.CardContainerRepository;

public class CreateCardUseCase {
    private final CardContainerRepository cardContainerRepository;

    public CreateCardUseCase(CardContainerRepository cardContainerRepository) {
        this.cardContainerRepository = cardContainerRepository;
    }

    public void createCard(int userId) {
        CardBoard container = cardContainerRepository.findByUserId(userId);
        container.createCard();
    }
}
