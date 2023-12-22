package project.wedding.usecase;

import project.wedding.domain.CardBoard;
import project.wedding.domain.CardContainerRepository;

public class CreateCardUseCase {
    private final CardContainerRepository cardContainerRepository;

    public CreateCardUseCase(CardContainerRepository cardContainerRepository) {
        this.cardContainerRepository = cardContainerRepository;
    }

    public void createCard(String userId) {
        CardBoard container = cardContainerRepository.findByUserId(userId);
        boolean isCreated = container.createCard();
        System.out.println("ID: " + userId + "의 카드가 생성됐습니다.");
        if (!isCreated) {
            throw new RuntimeException("create fail");
        }
    }
}
