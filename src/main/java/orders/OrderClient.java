package orders;

import user.Client;
import user.UserClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static orders.OrderGenerator.*;

public class OrderClient extends Client {
    private static final String ORDERS_USER = "/api/orders";
    public static final Order WRONG_ORDER_CHECK = wrongOrder();
    public static final Order CORRECT_ORDER_CHECK = correctOrder();

    @Step("Create order")
    public ValidatableResponse createOrder(Boolean isLogin, Order ingredients) {
        try {
            return given()
                    .spec(Client.getBaseSpec())
                    .auth().oauth2(UserClient.getToken())
                    .body(ingredients)
                    .post(ORDERS_USER)
                    .then();
        } catch (Exception e) {
            System.out.println("Wrong auth token");
            return null;
        }
    }

    @Step("Create order without ingredients")
    public ValidatableResponse createOrderNoIngredients() {
        return given()
                .spec(Client.getBaseSpec())
                .auth().oauth2(UserClient.getToken())
                .post(ORDERS_USER)
                .then();
    }

    @Step("Getting list of orders")
    public ValidatableResponse getOrders(Boolean isLogin) {
        try {
            return given()
                    .spec(Client.getBaseSpec())
                    .auth().oauth2(UserClient.getToken())
                    .get(ORDERS_USER).then();
        } catch (Exception e) {
            System.out.println("Wrong auth token");
            return null;
        }
    }
}