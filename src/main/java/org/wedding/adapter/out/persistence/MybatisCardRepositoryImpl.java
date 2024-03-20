package org.wedding.adapter.out.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.wedding.application.port.out.repository.CardRepository;
import org.wedding.domain.card.Card;

@Repository
@Mapper
public interface MybatisCardRepositoryImpl extends CardRepository {
    @Override
    int save(Card card);

    @Override
    boolean existsByCardTitle(String cardTitle);
}
