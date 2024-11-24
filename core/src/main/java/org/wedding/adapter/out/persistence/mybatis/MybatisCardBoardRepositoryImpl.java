package org.wedding.adapter.out.persistence.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.wedding.application.port.out.repository.CardBoardRepository;
import org.wedding.domain.cardboard.CardBoard;

@Repository
@Mapper
public interface MybatisCardBoardRepositoryImpl extends CardBoardRepository {

    @Override
    int save(CardBoard cardBoard);

    @Override
    void addCardIds(int cardBoardId, int cardId);

    @Override
    CardBoard findCardBoardByUserId(int userId);

    @Override
    List<Integer> findCardIdsByUserId(int userId);
}
