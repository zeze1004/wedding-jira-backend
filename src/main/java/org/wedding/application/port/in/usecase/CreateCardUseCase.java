package org.wedding.application.port.in.usecase;

import org.wedding.adapter.in.web.dto.CreateCardRequest;

public interface CreateCardUseCase {

    void createCard(CreateCardRequest request);
}
