package com.denizcanbagdatlioglu.self.wiki.repository;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Figure;
import com.denizcanbagdatlioglu.self.wiki.domain.repository.IFigureRepository;
import com.denizcanbagdatlioglu.self.wiki.domain.valueobject.FigureType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;


public class FigureRepository<T extends Figure> implements IFigureRepository<T> {

    private final JdbcTemplate jdbcTemplate;

    private final String figureType;

    private final Figure.FigureBuilder<T> builder;

    public FigureRepository(JdbcTemplate jdbcTemplate, FigureType figureType, Figure.FigureBuilder<T> builder) {
        this.jdbcTemplate = jdbcTemplate;
        this.figureType = figureType.name();
        this.builder = builder;
    }

    @Override
    public Optional<T> getFigureByID(ID id) {
        RowMapper<T> mapper = (rs, _) -> builder
                .id(ID.of(rs.getString("id")))
                .name(rs.getString("name"))
                .brief(rs.getString("brief"))
                .description(rs.getString("description"))
                .build();
        String sql = "select * from figures where figure = ?::figuretype and id = ?";
        try {
            T figure = jdbcTemplate.queryForObject(sql, mapper, figureType, id.asUuid());
            return Optional.of(figure);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<T> getFiguresWithPagination(int count, int skipCount) {
        RowMapper<T> mapper = (rs, _) -> builder
                .id(ID.of(rs.getString("id")))
                .name(rs.getString("name"))
                .brief(rs.getString("brief"))
                .build();
        String sql = "select id, name, brief from figures where figure = ?::figuretype order by id limit ? offset ?";
        try {
            return jdbcTemplate.query(sql, mapper, figureType, count, skipCount);
        } catch (DataAccessException e) {
            return List.of();
        }
    }
}
