import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orders.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserClient;

import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static user.UserClient.delete;


public class OrdersListTest {
    @Before
    public void setUp() {
        UserClient.createUser(UserClient.user);
    }

    @Test
    @DisplayName("Получение списка заказов c авторизацией")
    public void listOrdersWithAuthorizationTest() {
        OrderClient ordersClient = new OrderClient();
        ValidatableResponse response = ordersClient.getOrders(true);
        int statusCode = response.extract().statusCode();
        assertEquals("Статус код не 200", SC_OK, statusCode);
        response.assertThat().body("success", notNullValue());
    }

    @Test
    @DisplayName("Получение списка без авторизации")
    public void listOrdersTest() {
        OrderClient ordersClient = new OrderClient();
        ValidatableResponse response = ordersClient.getOrders(false);
        int statusCode = response.extract().statusCode();
        assertEquals("You should be authorised", SC_UNAUTHORIZED, statusCode);
        response.assertThat().body("success", notNullValue());
    }

    @After
    public void deleteUser() {
        delete();
    }
}