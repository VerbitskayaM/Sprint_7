package clients;

import dto.Courier;
import dto.Login;
import dto.Order;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;

public class ScooterClient {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    private static final String ORDERS_PATH = "/api/v1/orders";

    private static ValidatableResponse baseGetRequest(String path) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        return given()
                .baseUri(BASE_URI)
                .header("Content-type", "application/json")
                .when()
                .get(path)
                .then()
                .log().ifValidationFails();
    }

    private static ValidatableResponse basePostRequest(String path, Object reqObj) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        return given()
                .baseUri(BASE_URI)
                .header("Content-type", "application/json")
                .body(reqObj)
                .when()
                .post(path)
                .then()
                .log().ifValidationFails();
    }

    public static ValidatableResponse baseDeleteRequest(String path) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        return given()
                .baseUri(BASE_URI)
                .when()
                .delete(path)
                .then()
                .log().ifValidationFails();
    }

    @Step("Получаем список заказов")
    public static ValidatableResponse getOrderList() {
        return baseGetRequest(ORDERS_PATH);
    }

    @Step("Создаём заказ")
    public static ValidatableResponse createOrder(Order order) {
        return basePostRequest(ORDERS_PATH, order);
    }

    @Step("Создаём курьера")
    public static ValidatableResponse createCourier(Courier courier) {
        return basePostRequest(COURIER_PATH, courier);
    }

    @Step("Выполняем логин в аккаунт курьера")
    public static ValidatableResponse courierLogin(Login login) {
        return basePostRequest(COURIER_LOGIN_PATH, login);
    }

    @Step("Удаляем курьера")
    public static void deleteCourier(Courier courier) {
        ValidatableResponse response = basePostRequest(COURIER_LOGIN_PATH, courier);
        String id = response.extract().jsonPath().getString("id");
        baseDeleteRequest(COURIER_LOGIN_PATH + id);
    }
}
