import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Praktikum.Courier.*;

public class CreateCourierTest {
    int courierId;
    protected CourierRandom courierGenerateRandomData = new CourierRandom();
    private CourierData courierInfo;
    protected CourierProcedure courierSteps;
    private CourierCreate courierAssertVoid;
    private CourierRegister courierLoginCredintals;

    @Before
    @Step("Создание тестовых данных для курьера")
    public void setUp() {
        courierSteps = new CourierProcedure();
        courierInfo = courierGenerateRandomData.createCourierWithRandomData();
        courierSteps.createCourier(courierInfo);
        courierLoginCredintals = CourierRegister.from(courierInfo);
        courierAssertVoid = new CourierCreate();
    }

    @After
    @Step("Удаление созданного курьера")
    public void cleanData() {
        courierSteps.courierDelete(courierId);
    }

    @DisplayName("Тест на успешное создание курьера")
    @Description("Логин с правильными данными")
    @Test
    public void testSuccessCourierLogin() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(courierLoginCredintals);
        courierId = courierLogin.extract().path("id");
        courierAssertVoid.successLoginCourierAndTakeId(courierLogin);
    }

    @DisplayName("Тест на ошибку авторизации курьера без учётных данных")
    @Description("Авторизация с пустыми данными")
    @Test
    public void testErrorCourierLoginWithEmptyCreds() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new CourierRegister("", ""));
        courierAssertVoid.errorLoginCourierWithoutCredentials(courierLogin);
    }

    @DisplayName("Тест на ошибку авторизации курьера с несуществующеми учётнвми данными")
    @Description("Авторизация с несуществующими данными")
    @Test
    public void testErrorCourierLoginWithDoesNotExistCredintals() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new CourierRegister("f", "s"));
        courierAssertVoid.errorLoginCourierWithNotValidCredintals(courierLogin);
    }

    @DisplayName("Тест на ошибку авторизации курьера с пустым полем логина")
    @Description("Авторизация с пустым логином")
    @Test
    public void testErrorLoginCourierWithEmptyLogin() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new CourierRegister("", courierInfo.getPassword()));
        courierAssertVoid.errorLoginCourierWithoutCredentials(courierLogin);
    }

    @DisplayName("Тест на ошибку авторизации курьера с пустым полем пароля")
    @Description("Авторизация с пустым паролем")
    @Test
    public void testErrorLoginCourierWithEmptyPassword() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new CourierRegister(courierInfo.getLogin(), ""));
        courierAssertVoid.errorLoginCourierWithoutCredentials(courierLogin);
    }

}
