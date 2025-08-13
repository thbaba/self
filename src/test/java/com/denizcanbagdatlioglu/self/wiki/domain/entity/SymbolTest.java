package com.denizcanbagdatlioglu.self.wiki.domain.entity;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Symbol entity test")
public class SymbolTest {

    @Test
    @DisplayName("Should build symbol with all fields")
    public void shouldBuildSymbol() {
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

    @Test
    @DisplayName("Should not build symbol without id")
    public void shouldNotBuildSymbol() {
        Assertions.assertThatThrownBy(() -> Symbol.builder().build())
                .isInstanceOf(IllegalStateException.class);
    }

}
