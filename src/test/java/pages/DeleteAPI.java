package pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteAPI {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    public DeleteAPI() {
        RestAssured.baseURI = BASE_URL;
    }

    //Delete
    public Response deleteBooking(int id, String token) {
        return given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
            .when()
                .delete("/booking/" + id)
            .then()
                .extract().response();
    }
}
