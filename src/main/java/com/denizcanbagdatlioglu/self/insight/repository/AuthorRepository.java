package com.denizcanbagdatlioglu.self.insight.repository;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.insight.domain.entity.InsightAuthor;
import com.denizcanbagdatlioglu.self.insight.domain.repository.IAuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;


@RequiredArgsConstructor
public class AuthorRepository implements IAuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<InsightAuthor> findAuthorByID(ID id) {
        String sql = "select * from users where id = ?";
        RowMapper<InsightAuthor> rowMapper = (rs, _) ->
                InsightAuthor
                        .builder()
                        .id(ID.of(rs.getString("id")))
                        .birthDate(rs.getDate("birth_date").toLocalDate())
                        .build();

        try {
            InsightAuthor author = jdbcTemplate.queryForObject(sql, rowMapper, id.asUuid());
            return Optional.of(author);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
