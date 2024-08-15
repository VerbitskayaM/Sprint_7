package tests;

import clients.ScooterClient;
import dto.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {
    public static final String LOGIN = RandomStringUtils.randomAlphabetic(10);
    public static final String PASSWORD = RandomStringUtils.randomNumeric(6);
    Courier courier;

    @Before
    public void setUp() {
        courier = Courier.builder()
                .login(RandomStringUtils.randomAlphabetic(10))
                .password(RandomStringUtils.randomNumeric(6))
                .build();

        ScooterClient.createCourier(courier);
    }

    @Test
    @DisplayName("Create duplicate courier")
    public void duplicateCourierTest() {
        ValidatableResponse response = ScooterClient.createCourier(courier);
        response.statusCode(HttpStatus.SC_CONFLICT)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }

    @Test
    @DisplayName("Create courier with null login field")
    public void createCourierWithoutLoginTest() {
        ValidatableResponse response = ScooterClient.createCourier(Courier.builder()
                .login(null)
                .password(PASSWORD)
                .build());
        response.statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Create valid fields courier")
    public void validDataTest() {
        Courier newCourier = Courier.builder()
                .login(LOGIN)
                .password(PASSWORD)
                .build();
        ValidatableResponse response = ScooterClient.createCourier(newCourier);
        response.statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .body("ok", equalTo(true));
    }

    @After
    public void tearDown() {
        if (courier != null) {
            ScooterClient.deleteCourier(courier);
        }
    }
}
