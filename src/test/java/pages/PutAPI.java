package pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PutAPI {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    public PutAPI() {
        RestAssured.baseURI = BASE_URL;
    }

    //Put
    public Response updateBooking(int id, String token, String bodyJson) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .header("Authorization", "Basic")
                .body(bodyJson)
                .when()
                .put("/booking/" + id)
                .then()
                .extract().response();
    }
}
