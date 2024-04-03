package org.wedding.adapter.out.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.wedding.application.port.out.repository.CardBoardRepository;
import org.wedding.domain.CardBoard;

@Repository
@Mapper
public interface MybatisCardBoardRepository extends CardBoardRepository {

    @Override
    CardBoard save(CardBoard cardBoard);
    @Override
    CardBoard findByUserId(int userId);
}
