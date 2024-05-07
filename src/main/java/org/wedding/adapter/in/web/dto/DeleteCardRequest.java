package org.wedding.adapter.in.web.dto;

public record DeleteCardRequest(
    int cardId
) {

        public String toString() {
            return """
                DeleteCardRequest{
                    cardId='%s'
                }
                """.formatted(cardId);
        }
}
