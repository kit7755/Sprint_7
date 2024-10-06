package Praktikum.Courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;
import static Praktikum.Constants.BASE_URL;
import static Praktikum.Constants.DELETE_COURIER;
import static Praktikum.Constants.POST_COURIER_CREATE;
import static Praktikum.Constants.POST_COURIER_LOGIN;

public class CourierProcedure {
    public static RequestSpecification requestSpec() {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

    @Step("Регистрация нового курьера")
    public ValidatableResponse createCourier(CourierData courierInfo) {
        return requestSpec()
                .body(courierInfo)
                .when()
                .post(POST_COURIER_CREATE)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse courierAuthorization(CourierRegister courierInfo) {
        return requestSpec()
                .body(courierInfo)
                .when()
                .post(POST_COURIER_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse courierDelete(int courierId) {
        return requestSpec()
                .when()
                .delete(DELETE_COURIER + courierId)
                .then();
    }
}