package org.wedding.domain;

import org.wedding.domain.cardboard.CardBoard;

public interface CardContainerRepository {
    CardBoard findByUserId(String userId);
}
