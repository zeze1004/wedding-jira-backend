package project.wedding.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import project.wedding.domain.CardBoard;

@Repository
public class CardBoardRepository {

    private final JdbcTemplate jdbcTemplate;

    public CardBoardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createCardBoard(String cardBardId, String userId) {
        String createSql = "insert into cardBoard (cardBoardId, userId) values (?, ?)";
        jdbcTemplate.update(createSql, cardBardId, userId);
    }

    public CardBoard findByUserId(String userId) {
        String readSql = "select * from cardBoard where userId = ?";
        return jdbcTemplate.queryForObject(readSql, (rs, rowNum) -> new CardBoard()); // rowmapper를 사용해보자
    }
}
