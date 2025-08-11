package com.denizcanbagdatlioglu.self.auth.domain.valueobject;

import com.denizcanbagdatlioglu.self.auth.domain.exception.InvalidPasswordException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Raw password test.")
public class RawPasswordTest {

    @Test
    @DisplayName("Password null test.")
    void passwordNullTest() {
        RawPassword testPassword = new RawPassword(null);
        assertThatThrownBy(testPassword::confirm)
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Password cannot be null.");
    }

    @Test
    void passwordLengthTest() {
        RawPassword testPassword = new RawPassword("");
        assertThatThrownBy(testPassword::confirm)
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Password length must be at least 8 characters.");
    }

    @Test
    void passwordNoNumericTest() {
        RawPassword testPassword = new RawPassword("<PASSWORD>");
        assertThatThrownBy(testPassword::confirm)
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Password must contain at least one numeric character.");
    }

    @Test
    void passwordNoLowerCaseTest() {
        RawPassword testPassword = new RawPassword("<PASSWORD1>");
        assertThatThrownBy(testPassword::confirm)
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Password must contain at least one lowercase character.");
    }

    @Test
    void passwordNoUpperCaseTest() {
        RawPassword testPassword = new RawPassword("<password1>");
        assertThatThrownBy(testPassword::confirm)
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Password must contain at least one uppercase character.");
    }

    @Test
    void passwordNoSpecialCharTest() {
        RawPassword testPassword = new RawPassword("PaSSwOrD1");
        assertThatThrownBy(testPassword::confirm)
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Password must contain at least one special character.");
    }

    @Test
    void passwordWhitespaceTest() {
        RawPassword testPassword = new RawPassword("<PaSS wOrD1>");
        assertThatThrownBy(testPassword::confirm)
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Password can not contain whitespace.");
    }
}
