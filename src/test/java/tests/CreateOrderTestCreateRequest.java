package tests;

import clients.ScooterClient;
import dto.Order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
@AllArgsConstructor
public class CreateOrderTestCreateRequest {
    private final String firstNameValue;
    private final String lastNameValue;
    private final String addressValue;
    private final int metroStationValue;
    private final String phoneValue;
    private final int rentTimeValue;
    private final String deliveryDateValue;
    private final String commentValue;
    private final List<String> colorValue;


    @Parameterized.Parameters
    public static Object[][] getTestDataCreateOrder() {
        return new Object[][]{
                {"Mary", "Fox", "44 Cedar Avenue", 1, "+7 902 012 12 13", 5, "2024-08-15", "", List.of("BLACK")},
                {"Mary", "Fox", "44 Cedar Avenue", 3, "+7 902 012 12 13", 5, "2024-08-12", "", List.of("GREY")},
                {"Mary", "Fox", "44 Cedar Avenue", 4, "+7 902 012 12 13", 5, "2024-08-01", "", List.of("GREY", "BLACK")},
                {"Mary", "Fox", "44 Cedar Avenue", 2, "+7 902 012 12 13", 5, "2024-08-10", "Любой цвет", null}
        };
    }

    @Test
    @DisplayName("Create order")
    public void createOrderTest() {
        Order order = Order.builder()
                .firstName(firstNameValue)
                .lastName(lastNameValue)
                .address(addressValue)
                .metroStation(metroStationValue)
                .phone(phoneValue)
                .rentTime(rentTimeValue)
                .deliveryDate(deliveryDateValue)
                .comment(commentValue)
                .color(colorValue)
                .build();
        ValidatableResponse response = ScooterClient.createOrder(order);
        response.statusCode(HttpStatus.SC_CREATED);
        Assert.assertNotNull(response.extract().jsonPath().getString("track"));
    }
}
