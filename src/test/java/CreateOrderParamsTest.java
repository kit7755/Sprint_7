import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import Praktikum.Order.OrderData;
import Praktikum.Order.OrderPhase;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)

public class CreateOrderParamsTest {
    private final List<String> colour;
    private OrderPhase orderSteps;
    int track;

    public CreateOrderParamsTest(List<String> colour) {
        this.colour = colour;
    }
    @Before
    public void setUp(){
        orderSteps = new OrderPhase();
    }

    @After
    public void cleanUp(){
        orderSteps.cancellationOrder(track);
    }

    @Parameterized.Parameters
    public static Object[][] selectScooterColour(){
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("BLACK","GREY")},
                {List.of("GREY")},
                {List.of()}
        };
    }

    @DisplayName("Успешное создание нового заказа")
    @Description("Создаем заказ с разными цветами")
    @Test
    public void createOrderWithDiffColours(){
        OrderData orderInfo = new OrderData(colour);
        ValidatableResponse response = orderSteps.successOrderCreate(orderInfo);
        track = response.extract().path("track");
        response.assertThat().statusCode(201).body("track", is(notNullValue()));
    }
}
