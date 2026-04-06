package pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthAPI {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    public AuthAPI() {
        RestAssured.baseURI = BASE_URL;
    }

    public Response createToken(String payloadJson) {
        return given()
                .header("Content-Type", "application/json")
                .body(payloadJson)
                .when()
                .post("/auth")
                .then()
                .extract().response();
    }
}

