package com.denizcanbagdatlioglu.self.common.regex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Regex executor test.")
public class RegexExecutorTest {

    @Test
    @DisplayName("At least one found test.")
    public void atLeastOneFoundTest() {

        String value = "1234567890";
        String regex = "\\d";

        boolean result = RegexExecutor.atLeastOne(regex, value);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("At least one not found test.")
    public void atLeastOneNotFoundTest() {
        String value = "1234567890";
        String regex = "a";

        boolean result = RegexExecutor.atLeastOne(regex, value);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("No one on failure test.")
    public void noOneOnFailureTest() {
        String value = "1234567890";
        String regex = "1";

        boolean result = RegexExecutor.noOne(regex, value);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("No one on success test.")
    public void noOneOnSuccessTest() {
        String value = "1234567890";
        String regex = "a";

        boolean result = RegexExecutor.noOne(regex, value);

        assertThat(result).isTrue();
    }

}
