package project.wedding.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isNotExistId(String userId) {
        String readSql = "select * from user where userId = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, userId).isEmpty();
    }

    public void save(String userId) {
        String createSql = "insert into user (userId) values (?)";
        jdbcTemplate.update(createSql, userId);
    }
}
