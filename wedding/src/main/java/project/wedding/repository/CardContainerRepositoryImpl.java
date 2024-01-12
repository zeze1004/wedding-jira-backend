package project.wedding.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import project.wedding.domain.CardBoard;

@Repository
public class CardContainerRepositoryImpl implements CardContainerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CardContainerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CardBoard findByUserId(String userId) {
        return new CardBoard();
    }

    public boolean isNotUser(String userId) {
        String readSql = "select * from user where userId = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, userId).isEmpty();
    }
}
