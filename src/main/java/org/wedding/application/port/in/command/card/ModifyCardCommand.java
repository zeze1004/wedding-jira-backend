package org.wedding.application.port.in.command.card;

import java.time.LocalDateTime;
import java.util.Optional;

import org.wedding.domain.CardStatus;

import lombok.Builder;

@Builder
public record ModifyCardCommand(

    Optional<String> cardTitle,
    Optional<Long> budget,
    Optional<LocalDateTime> deadline,
    Optional<CardStatus> cardStatus
) {

        public ModifyCardCommand(String cardTitle, Long budget, LocalDateTime deadline, CardStatus cardStatus) {
            this(Optional.ofNullable(cardTitle), Optional.ofNullable(budget), Optional.ofNullable(deadline), Optional.ofNullable(cardStatus));
        }

        public String toString() {
            return """
                ModifyCardCommand{
                    cardTitle='%s',
                    budget='%s',
                    deadline='%s',
                    cardStatus='%s'
                }
                """.formatted(cardTitle, budget, deadline, cardStatus);
        }
}
