package org.wedding.config.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.wedding.application.port.in.command.card.HasCardId;
import org.wedding.application.service.CardService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class CardCheckAspect {

    private final CardService cardService;

    @Before("@annotation(org.wedding.config.anotation.CheckCardExistence) && args(command,..)")
    public void checkCardExistenceAdvice(HasCardId command) {
        if (command != null) {
            int cardId = command.cardId();
            cardService.checkCardExistence(cardId);
        }
    }
}
