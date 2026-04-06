package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pages.GetAPI;
import pages.PostAPI;
import static org.hamcrest.Matchers.*;
import utils.JsonUtils;

public class GetTest {

    GetAPI getAPI = new GetAPI();
    PostAPI postAPI = new PostAPI();
 
    @Test
    public void testGetAllBookings() {
        Response response = getAPI.getAllBookings();
        response.then().statusCode(200).log().all();
    }

    @Test
    public void testGetBookingById() {
        // Criar booking antes
        String payloadBooking = JsonUtils.readJsonFile("src/test/resources/payloads/body_Post.json");
        Response bookingResponse = postAPI.createBooking(payloadBooking);
        int bookingId = bookingResponse.jsonPath().getInt("bookingid");

        // Validar GET
        Response response = getAPI.getBookingById(bookingId);
        response.then()
                .statusCode(200)
                .body("firstname", notNullValue())
                .body("lastname", notNullValue())
                .body("totalprice", instanceOf(Integer.class))
                .body("depositpaid", instanceOf(Boolean.class));
    }


    @Test
    public void testGetBookingsByFirstname() {
        Response response = getAPI.getBookingsByFirstname("James");
        response.then().statusCode(200);
    }

    @Test
    public void testGetBookingsByLastname() {
        Response response = getAPI.getBookingsByLastname("Betoni");
        response.then().statusCode(200);
    }

    @Test
    public void testGetBookingsByCheckin() {
        Response response = getAPI.getBookingsByCheckin("2020-12-29");
        response.then().statusCode(200);
    }

    @Test
    public void testGetBookingsByCheckout() {
        Response response = getAPI.getBookingsByCheckout("2021-01-03");
        response.then().statusCode(200);
    }

    @Test
    public void testGetBookingByInvalidId() {
        Response response = getAPI.getBookingById(-999);
        response.then()
                .statusCode(404)
                .body(equalTo("Not Found"));
    }

    @Test
    public void testGetBookingsByInvalidFirstname() {
        Response response = getAPI.getBookingsByFirstname("111");
        response.then()
                .statusCode(200)
                .body("", hasSize(0));
    }

    @Test
    public void testGetBookingsByInvalidLastname() {
        Response response = getAPI.getBookingsByLastname("111");
        response.then()
                .statusCode(200)
                .body("", hasSize(0));
    }

    @Test
    public void testGetBookingsByInvalidCheckin() {
        Response response = getAPI.getBookingsByCheckin("teste");
        response.then()
                .statusCode(500)
                .body(equalTo("Internal Server Error"));
    }

    @Test
    public void testGetBookingsByInvalidCheckout() {
        Response response = getAPI.getBookingsByCheckout("teste");
        response.then()
                .statusCode(500)
                .body(equalTo("Internal Server Error"));
    }


}
