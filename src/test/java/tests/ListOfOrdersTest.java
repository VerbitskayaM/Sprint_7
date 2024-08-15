package tests;

import clients.ScooterClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

public class ListOfOrdersTest {

    @Test
    @DisplayName("List of orders is not empty")
    public void ordersFieldInListOfOrdersTest() {
        ValidatableResponse response = ScooterClient.getOrderList();
        Assert.assertNotNull(response.extract().jsonPath().getList("orders"));
    }
}
