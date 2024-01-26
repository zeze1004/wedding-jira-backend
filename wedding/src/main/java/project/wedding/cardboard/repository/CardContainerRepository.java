package project.wedding.cardboard.repository;

import project.wedding.cardboard.domain.CardBoard;

public interface CardContainerRepository {
    CardBoard findByUserId(int id);
}
