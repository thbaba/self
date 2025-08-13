package com.denizcanbagdatlioglu.self.wiki.application;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Archetype;
import com.denizcanbagdatlioglu.self.wiki.repository.FigureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FigureService<Archetype> unit test.")
public class ArchetypeServiceTest {

    @Mock
    private FigureRepository<Archetype> repository;

    @InjectMocks
    private FigureService<Archetype> service;

    private List<Archetype> prepared;

    private int page;

    private int pageSize;

    @BeforeEach
    public void setUp() {
        prepared = Arrays.asList(
                Archetype.builder().id(ID.random()).name("N1").build(),
                Archetype.builder().id(ID.random()).name("N2").build(),
                Archetype.builder().id(ID.random()).name("N3").build()
        );

        pageSize = 10;
        page = 0;
    }

    @Test
    @DisplayName("Get list of archetype test")
    public void getListOfArchetype() {
        when(repository.getFiguresWithPagination(pageSize, page)).thenReturn(prepared);

        List<Archetype> archetypes = service.getFigures(pageSize, page);

        assertThat(archetypes).containsExactlyElementsOf(prepared);
        verify(repository).getFiguresWithPagination(pageSize, page);
    }
}
