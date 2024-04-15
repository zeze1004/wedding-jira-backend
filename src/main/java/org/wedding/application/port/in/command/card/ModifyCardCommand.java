package org.wedding.application.port.in.command.card;

import java.time.LocalDate;
import java.util.Optional;

import org.wedding.domain.CardStatus;

import lombok.Builder;

@Builder
public record ModifyCardCommand(

    int userId,
    Optional<String> cardTitle,
    Optional<Long> budget,
    Optional<LocalDate> deadline,
    Optional<CardStatus> cardStatus
) {

        public ModifyCardCommand(int userId, String cardTitle, Long budget, LocalDate deadline, CardStatus cardStatus) {
            this(userId, Optional.ofNullable(cardTitle), Optional.ofNullable(budget), Optional.ofNullable(deadline), Optional.ofNullable(cardStatus));
        }

        public String toString() {
            return """
                ModifyCardCommand{
                    userId='%s',
                    cardTitle='%s',
                    budget='%s',
                    deadline='%s',
                    cardStatus='%s'
                }
                """.formatted(userId, cardTitle, budget, deadline, cardStatus);
        }
}
