package org.wedding.application.port.in.usecase;

import org.wedding.adapter.in.web.dto.ModifyCardRequest;
import org.wedding.domain.card.Card;

public interface ModifyCardUseCase {

    void modifyCard(Card card, ModifyCardRequest request);
}
