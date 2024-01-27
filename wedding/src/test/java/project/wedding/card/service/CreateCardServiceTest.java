package project.wedding.card.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import project.wedding.cardboard.domain.CardBoard;
import project.wedding.cardboard.repository.CardContainerRepository;
import project.wedding.user.domain.User;

@ExtendWith(MockitoExtension.class)
class CreateCardServiceTest {
    @Mock
    private CardContainerRepository cardContainerRepository;

    @Mock
    User user;

    private CreateCardService createCardService;

    @BeforeEach
    void setUp() {
        createCardService = new CreateCardService(cardContainerRepository);
    }

    @DisplayName("user가 카드를 생성합니다.")
    @Test
    void createCardUseCase() throws Exception {
        // given
        when(cardContainerRepository.findByUserId(user.getId())).thenReturn(new CardBoard());

        // when
        createCardService.createCard(user.getId());

        // then
        verify(cardContainerRepository, times(1)).findByUserId(user.getId());
    }

}
