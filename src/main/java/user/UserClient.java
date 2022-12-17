package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {
    private static final String CREATE_USER = "/api/auth/register";
    private static final String LOGIN_USER = "/api/auth/login";
    private static final String DELETE_USER = "/api/auth/user";

    public static final User user = UserGenerator.generatorCoOne();
    public static final User wrongUser = UserGenerator.generatorCoTwo();
    public static final User noEmailUser = UserGenerator.generatorCoThree();

    @Step("Create user")
    public static ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(CREATE_USER)
                .then();
    }

    @Step("Login user")
    public static ValidatableResponse login(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(LOGIN_USER)
                .then();
    }

    @Step("Delete user")
    public static void delete() {
        String bearerToken = getToken();
        given()
                .spec(getBaseSpec())
                .auth().oauth2(bearerToken)
                .delete(DELETE_USER);
    }

    @Step("Take access token")
    public static String getToken() {
        return login(user)
                .extract()
                .body()
                .path("accessToken")
                .toString().replaceAll("Bearer ", "");
    }
}