package project.wedding.repository;

import project.wedding.domain.CardBoard;

public interface CardContainerRepository {
    CardBoard findByUserId(String userId);
}
