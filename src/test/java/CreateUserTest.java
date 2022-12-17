import user.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;
import static user.UserClient.createUser;

public class CreateUserTest {
    protected boolean deleteCheck;

    @Before
    public void setUp() {
        deleteCheck = true;
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createUserTest() {
        ValidatableResponse response = createUser(UserClient.user);
        int statusCode = response.extract().statusCode();
        assertEquals("Статус код не 200", SC_OK, statusCode);
        boolean isTrue = response.extract().path("success");
        assertTrue(isTrue);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createSameUserTest() {
        ValidatableResponse response1 = createUser(UserClient.user);
        ValidatableResponse response2 = createUser(UserClient.user);
        int statusCode = response2.extract().statusCode();
        assertEquals("Дубликат создан", SC_FORBIDDEN, statusCode);
        boolean isFalse = response2.extract().path("success");
        assertFalse(isFalse);
    }

    @Test
    @DisplayName("Создание пользователя, без заполнения обязательного поля")
    public void createInvalidUserTest() {
        deleteCheck = false;
        ValidatableResponse response = UserClient.createUser(UserClient.wrongUser);
        int statusCode = response.extract().statusCode();
        assertEquals("Курьер создан без обязательного поля", SC_FORBIDDEN, statusCode);
        boolean isFalse = response.extract().path("success");
        assertFalse(isFalse);
    }

    @After
    public void deleteUser() {
        if (deleteCheck) {
            UserClient.delete();
        }
    }
}