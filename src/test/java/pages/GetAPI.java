package pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetAPI {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    public GetAPI() {
        RestAssured.baseURI = BASE_URL;
    }

    //GET
    public Response getAllBookings() {
        return given()
                .header("Accept", "application/json")
                .when()
                .get("/booking")
                .then()
                .extract().response();
    }

    public Response getBookingById(int id) {
        return given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/" + id)
                .then()
                .extract().response();
    }

    public Response getBookingsByFirstname(String firstname) {
        return given()
                .contentType("application/json")
                .when()
                .get("/booking?firstname=" + firstname)
                .then()
                .extract().response();
    }

    public Response getBookingsByLastname(String lastname) {
        return given()
                .contentType("application/json")
                .when()
                .get("/booking?lastname=" + lastname)
                .then()
                .extract().response();
    }

    public Response getBookingsByCheckin(String checkin) {
        return given()
                .contentType("application/json")
                .when()
                .get("/booking?checkin=" + checkin)
                .then()
                .extract().response();
    }

    public Response getBookingsByCheckout(String checkout) {
        return given()
                .contentType("application/json")
                .when()
                .get("/booking?checkout=" + checkout)
                .then()
                .extract().response();
        }

}
