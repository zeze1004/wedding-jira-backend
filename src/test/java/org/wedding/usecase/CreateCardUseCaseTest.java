package org.wedding.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.wedding.domain.cardboard.CardBoard;
import org.wedding.domain.CardContainerRepository;
import org.wedding.usecase.CreateCardUseCase;
import org.wedding.domain.user.User;

@Disabled
@ExtendWith(MockitoExtension.class)
@DisplayName("user가 카드를 생성합니다.")
class CreateCardUseCaseTest {
    @Mock
    CardContainerRepository cardContainerRepository;

    @Test
    void createCardUseCase() throws Exception {

        User user1 = new User("ODEE1");

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardContainerRepository);

        when(cardContainerRepository.findByUserId(String.valueOf(user1.getId()))).thenReturn(new CardBoard());
        createCardUseCase.createCard(user1.getName());

        verify(cardContainerRepository, times(1)).findByUserId(String.valueOf(user1.getId()));

        assertThat(cardContainerRepository.findByUserId(String.valueOf(user1.getId())).getCardById(1).getCardId()).isEqualTo(1);
    }

}
