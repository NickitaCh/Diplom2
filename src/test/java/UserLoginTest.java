import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.*;
import static user.UserClient.*;

public class UserLoginTest {

    @Before
    public void setUp() {
        createUser();
    }

    @Test
    @DisplayName("Логин пользователя в системе")
    public void loginUserTest() {
        ValidatableResponse loginResponse = UserClient.login();
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код не 201", SC_OK, statusCode);
        boolean isTrue = loginResponse.extract().path("success");
        assertTrue(isTrue);
    }

    @Test
    @DisplayName("Логин с несуществующей парой логин-пароль")
    public void noLoginAndPassUserTest() {
        ValidatableResponse loginResponse = UserClient.loginWrongUser();
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код не 401", SC_UNAUTHORIZED, statusCode);
        boolean isFalse = loginResponse.extract().path("success");
        assertFalse(isFalse);
    }

    @After
    public void deleteUser() {
        delete();
    }
}