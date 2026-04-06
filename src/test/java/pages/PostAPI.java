package pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostAPI {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    public PostAPI() {
        RestAssured.baseURI = BASE_URL;
    }

    //POST
    public Response createBooking(String bodyJson) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bodyJson)
                .when()
                .post("/booking")
                .then()
                .extract().response();
    }
}
