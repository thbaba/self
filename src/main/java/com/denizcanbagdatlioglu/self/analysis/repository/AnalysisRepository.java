package com.denizcanbagdatlioglu.self.analysis.repository;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IAnalysisRepository;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AnalysisRepository implements IAnalysisRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Analysis> analysisRowMapper = (rs, _) -> Analysis.builder()
            .id(ID.of(rs.getString("id")))
            .insight(rs.getString("insight"))
            .analysis(rs.getString("analysis")).build();

    @Override
    public Optional<String> findInsightByID(ID userID, ID insightID) {
        try {
            String sql = "select content from insights where id = ? and author_id = ?";

            String insight = jdbcTemplate.queryForObject(sql, (rs, _) -> rs.getString("content"), insightID.asUuid(), userID.asUuid());

            return Optional.of(insight);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<LocalDate> findBirthDateByID(ID userID) {
        try {
            String sql = "select birth_date from users where id = ?";

            LocalDate birthDate = jdbcTemplate.queryForObject(sql, (rs,_) -> rs.getDate("birth_date").toLocalDate(), userID.asUuid());

            return Optional.of(birthDate);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Analysis> findAnalysisByInsightID(ID userID, ID insightID) {
        String sql = "select analyses.id id, insights.content insight, analyses.analysis analysis from analyses " +
                "inner join insights on analyses.insight_id = insights.id " +
                "inner join users on analyses.user_id = users.id " +
                "where analyses.insight_id = ? and analyses.user_id = ?";

        try {
            Analysis analysis = jdbcTemplate.queryForObject(sql, analysisRowMapper, insightID.asUuid(), userID.asUuid());

            return Optional.of(analysis);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Analysis> findAnalyses(ID userID, int analysesCount) {
        String sql = "select analyses.id id, insights.content insight, analyses.analysis analysis from analyses " +
                "inner join insights on analyses.insight_id = insights.id " +
                "inner join users on insights.author_id = users.id " +
                "where analyses.user_id = ? limit ?";

        try {
            return jdbcTemplate.query(sql, analysisRowMapper, userID.asUuid(), analysesCount);
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public boolean saveAnalysis(ID userID, ID insightID, String analysis) {
        boolean exists = findAnalysisByInsightID(userID, insightID).isPresent();
        if(exists)
            return false;

        String sql = "insert into analyses(user_id, insight_id, analysis) " +
                "values (?, ?, ?)";

        try {
            return jdbcTemplate.update(sql, userID.asUuid(), insightID.asUuid(), analysis) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
