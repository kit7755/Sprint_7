package Praktikum.Courier;

import io.qameta.allure.Step;
import net.datafaker.Faker;

public class CourierRandom {
    static Faker faker = new Faker();

    @Step("Создание курьера со случайными данными")
    public CourierData createCourierWithRandomData() {
        return new CourierData(
                faker.name().name(),
                faker.internet().password(),
                faker.name().firstName()
        );
    }
}