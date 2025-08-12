package com.denizcanbagdatlioglu.self.wiki.domain.entity;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SymbolTest {

    @Test
    public void symbolBuilderTest() {
        ID id = ID.random();
        String name = "TestName";
        String brief = "TestBrief";
        String description = "TestDescription";

        Symbol sys = Symbol.builder()
                .id(id)
                .name(name)
                .brief(brief)
                .description(description)
                .build();

        assertThat(sys).extracting(Symbol::id).isEqualTo(id);
        assertThat(sys).extracting(Symbol::name).isEqualTo(name);
        assertThat(sys).extracting(Symbol::brief).isEqualTo(brief);
        assertThat(sys).extracting(Symbol::description).isEqualTo(description);
    }

}
