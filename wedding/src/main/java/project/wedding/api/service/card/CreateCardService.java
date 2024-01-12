package project.wedding.api.service.card;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.wedding.domain.CardBoard;
import project.wedding.repository.CardContainerRepository;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class CreateCardService {
    private final CardContainerRepository cardContainerRepository;
    private final Logger logger = Logger.getLogger(CreateCardService.class.getName());

    @Autowired
    public CreateCardService(CardContainerRepository cardContainerRepository) {
        this.cardContainerRepository = cardContainerRepository;
    }

    public void createCard(String userId) {
        CardBoard container = cardContainerRepository.findByUserId(userId);
        container.createCard();
        logger.info("ID: " + userId + "의 카드가 생성됐습니다.");
    }
}
