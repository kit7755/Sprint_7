import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Praktikum.Courier.*;

public class CourierSignInTest {
    int createdCourierId;
    protected CourierRandom courierRandomDataGenerator = new CourierRandom();
    private CourierData courierData;
    protected CourierProcedure courierActions;
    private CourierCreate courierAssertions;
    private CourierRegister courierLoginCredentials;

    @Before
    @Step("Создание тестовых данных для курьера")
    public void setUp() {
        courierActions = new CourierProcedure();
        courierData = courierRandomDataGenerator.createCourierWithRandomData();
        courierActions.createCourier(courierData);
        courierLoginCredentials = CourierRegister.from(courierData);
        courierAssertions = new CourierCreate();
    }

    @After
    @Step("Удаление созданного курьера")
    public void cleanData() {
        courierActions.courierDelete(createdCourierId);
    }

    @DisplayName("Тест на успешное создание курьера")
    @Description("Авторизация с правильными данными")
    @Test
    public void shouldAllowSuccessfulLoginWithValidCredentials() {
        ValidatableResponse courierLogin = courierActions.courierAuthorization(courierLoginCredentials);
        createdCourierId = courierLogin.extract().path("id");
        courierAssertions.successLoginCourierAndTakeId(courierLogin);
    }

    @DisplayName("Тест на ошибку вход курьера без учётных данных")
    @Description("Авторизация с пустыми данными")
    @Test
    public void shouldReturnErrorWhenLoginWithEmptyCredentials() {
        ValidatableResponse courierLogin = courierActions.courierAuthorization(new CourierRegister("", ""));
        courierAssertions.errorLoginCourierWithoutCredentials(courierLogin);
    }

    @DisplayName("Тест на ошибку вход курьера с несуществующими учётными данными")
    @Description("Авторизация с несуществующими данными")
    @Test
    public void shouldReturnErrorWhenLoginWithNonExistentCredentials() {
        ValidatableResponse courierLogin = courierActions.courierAuthorization(new CourierRegister("gh", "km"));
        courierAssertions.errorLoginCourierWithNotValidCredintals(courierLogin);
    }

    @DisplayName("Тест на ошибку авторизации курьера с пустым полем логина")
    @Description("Авторизация без логина")
    @Test
    public void shouldReturnErrorWhenLoginWithEmptyUsername() {
        ValidatableResponse courierLogin = courierActions.courierAuthorization(new CourierRegister("", courierData.getPassword()));
        courierAssertions.errorLoginCourierWithoutCredentials(courierLogin);
    }

    @DisplayName("Тест на ошибку авторизация курьера с пустым полем пароля")
    @Description("Авторизация с пустым паролем")
    @Test
    public void shouldReturnErrorWhenLoginWithEmptyPassword() {
        ValidatableResponse courierLogin = courierActions.courierAuthorization(new CourierRegister(courierData.getLogin(), ""));
        courierAssertions.errorLoginCourierWithoutCredentials(courierLogin);
    }
}