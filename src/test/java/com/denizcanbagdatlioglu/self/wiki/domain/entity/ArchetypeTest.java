package com.denizcanbagdatlioglu.self.wiki.domain.entity;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArchetypeTest {

    @Test
    public void archetypeBuilderTest() {
        ID id = ID.random();
        String name = "TestName";
        String brief = "TestBrief";
        String description = "TestDescription";

        Archetype sys = Archetype.builder()
                .id(id)
                .name(name)
                .brief(brief)
                .description(description)
                .build();

        assertThat(sys).extracting(Archetype::id).isEqualTo(id);
        assertThat(sys).extracting(Archetype::name).isEqualTo(name);
        assertThat(sys).extracting(Archetype::brief).isEqualTo(brief);
        assertThat(sys).extracting(Archetype::description).isEqualTo(description);

    }

}
