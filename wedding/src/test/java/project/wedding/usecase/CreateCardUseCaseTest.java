package project.wedding.usecase;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import project.wedding.domain.CardContainerRepository;
import project.wedding.domain.User;

import project.wedding.domain.CardBoard;

@ExtendWith(MockitoExtension.class)
@org.junit.jupiter.api.DisplayName("카드를 생성합니다.")
class CreateCardUseCaseTest {
    @Mock
    CardContainerRepository cardContainerRepository;


    @Test
    void createCardUseCase() throws Exception {

        User user1 = new User("ODEE1");
        User user2 = new User("ODEE2");

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardContainerRepository);

        when(cardContainerRepository.findByUserId(user1.getUserId())).thenReturn(new CardBoard());
        createCardUseCase.createCard("ODEE1");
        createCardUseCase.createCard("ODEE1");
        createCardUseCase.createCard("ODEE1");

        when(cardContainerRepository.findByUserId(user2.getUserId())).thenReturn(new CardBoard());
        createCardUseCase.createCard("ODEE2");
        createCardUseCase.createCard("ODEE2");
        createCardUseCase.createCard("ODEE2");
    }

}

