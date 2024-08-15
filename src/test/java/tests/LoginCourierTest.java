package tests;

import clients.ScooterClient;
import dto.Courier;
import dto.Login;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginCourierTest {
    public static final String LOGIN = RandomStringUtils.randomAlphabetic(10);
    public static final String PASSWORD = RandomStringUtils.randomNumeric(6);

    Courier courier;

    @Before
    public void setUp() {
        courier = Courier.builder()
                .login(LOGIN)
                .password(PASSWORD)
                .build();

        ScooterClient.createCourier(courier);
    }

    @Test
    @DisplayName("Successful login")
    public void successfulLoginTest() {
        ValidatableResponse response = ScooterClient.courierLogin(
                Login.builder()
                        .login(LOGIN)
                        .password(PASSWORD)
                        .build());
        response.statusCode(HttpStatus.SC_OK);
        Assert.assertNotNull(response.extract().jsonPath().getString("id"));
    }

    @Test
    @DisplayName("Login with null login field")
    public void withoutLoginTest() {
        ValidatableResponse response = ScooterClient.courierLogin(
                Login.builder()
                        .login(null)
                        .password(PASSWORD)
                        .build());
        response.statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login with empty password field")
    public void withoutPasswordTest() {
        ValidatableResponse response = ScooterClient.courierLogin(
                Login.builder()
                        .login(LOGIN)
                        .password("")
                        .build());
        response.statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login with non-existent data")
    public void nonExistentDataTest() {
        ValidatableResponse response = ScooterClient.courierLogin(
                Login.builder()
                        .login(RandomStringUtils.randomAlphabetic(10))
                        .password(RandomStringUtils.randomNumeric(6))
                        .build());
        response.statusCode(HttpStatus.SC_NOT_FOUND)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        ScooterClient.deleteCourier(courier);
    }
}
