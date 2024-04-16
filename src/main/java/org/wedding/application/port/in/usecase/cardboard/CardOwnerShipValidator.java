package org.wedding.application.port.in.usecase.cardboard;

public interface CardOwnerShipValidator {
    boolean checkCardOwner(int userId, int cardId);
}
