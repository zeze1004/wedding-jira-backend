package project.wedding.domain;

import org.springframework.stereotype.Repository;

@Repository
public class CardContainerRepositoryImpl implements CardContainerRepository {
    @Override
    public CardBoard findByUserId(String userId) {
        return new CardBoard();
    }
}
