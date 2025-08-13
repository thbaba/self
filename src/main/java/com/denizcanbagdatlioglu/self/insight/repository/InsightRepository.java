package com.denizcanbagdatlioglu.self.insight.repository;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.insight.domain.entity.Insight;
import com.denizcanbagdatlioglu.self.insight.domain.entity.InsightAuthor;
import com.denizcanbagdatlioglu.self.insight.domain.repository.IInsightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class InsightRepository implements IInsightRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Insight> rowMapper = (rs, _) ->
            Insight.builder()
                    .id(ID.of(rs.getString("id")))
                    .title(rs.getString("title"))
                    .date(rs.getTimestamp("date").toLocalDateTime())
                    .content(rs.getString("content"))
                    .author(InsightAuthor.builder()
                            .id(ID.of(rs.getString("author_id")))
                            .birthDate(rs.getDate("author_birth_date").toLocalDate())
                            .build())
                    .build();

    @Override
    public boolean saveInsight(Insight insight) {
        String sql = "insert into insights(id, title, date, author_id, content) values (?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                insight.id().asUuid(),
                insight.title(),
                Timestamp.valueOf(insight.date()),
                insight.author().id().asUuid(),
                insight.content()) > 0;
    }

    @Override
    public Optional<Insight> getInsightById(ID id) {
        String sql = "select it.id id, it.title title , it.date date, it.content content, " +
                "at.id author_id, at.birth_date author_birth_date" +
                "from insights it inner join users at on it.author_id = at.id " +
                "where it.id = ?";

        try {
            Insight insight = jdbcTemplate.queryForObject(sql, rowMapper, id.asUuid());
            return Optional.of(insight);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Insight> getInsights(int count, int skip) {
        String sql = "select it.id id, it.title title , it.date date, it.content content, " +
                "at.id author_id, at.birth_date author_birth_date " +
                "from insights it inner join users at on it.author_id = at.id " +
                "order by it.date limit ? offset ?";

        try {
            return jdbcTemplate.query(sql, rowMapper, count, skip);
        } catch (DataAccessException e) {
            return List.of();
        }
    }
}
