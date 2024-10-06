package Praktikum.Order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderAssert {
    @Step("Успешное получение списка заказов")
    public void successGetOrdersList(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}
