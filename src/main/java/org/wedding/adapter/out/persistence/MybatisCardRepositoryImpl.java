package org.wedding.adapter.out.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.wedding.application.port.out.repository.CardRepository;
import org.wedding.domain.CardStatus;
import org.wedding.domain.card.Card;

@Repository
@Mapper
public interface MybatisCardRepositoryImpl extends CardRepository {
    @Override
    int save(Card card);

    @Override
    boolean existsByCardTitle(String cardTitle);

    @Override
    boolean existsByCardId(int cardId);

    @Override
    Card findByCardId(int cardId);

    @Override
    Card findByCardTitle(String cardTitle);

    @Override
    List<Card> findByCardStatus(CardStatus cardStatus);


}
