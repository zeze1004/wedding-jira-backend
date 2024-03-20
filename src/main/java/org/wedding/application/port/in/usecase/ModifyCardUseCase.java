package org.wedding.application.port.in.usecase;

import org.wedding.adapter.in.web.dto.ModifyCardRequest;

public interface ModifyCardUseCase {

    void modifyCard(int card, ModifyCardRequest request);
}
