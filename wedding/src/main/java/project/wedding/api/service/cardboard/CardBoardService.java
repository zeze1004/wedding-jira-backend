package project.wedding.api.service.cardboard;

import java.util.UUID;

import org.springframework.stereotype.Service;

import project.wedding.domain.CardBoard;
import project.wedding.repository.CardBoardRepository;

@Service
public class CardBoardService {

    private final CardBoardRepository cardBoardRepository;

    public CardBoardService(CardBoardRepository cardBoardRepository) {
        this.cardBoardRepository = cardBoardRepository;
    }

    public void createCardBoard(String userId) {
        String cardBoardId = UUID.randomUUID().toString().substring(0,5) + "-" + userId;
        cardBoardRepository.createCardBoard(cardBoardId, userId);
    }

    public CardBoard findByUserId(String userId) {
        return cardBoardRepository.findByUserId(userId);
    }

}
