package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pages.DeleteAPI;
import pages.AuthAPI;
import pages.PostAPI;
import utils.JsonUtils; // import da classe utilitária

import static org.junit.jupiter.api.Assertions.assertEquals; // import do assert

public class DeleteTest {

    AuthAPI authAPI = new AuthAPI();
    PostAPI postAPI = new PostAPI();
    DeleteAPI deleteAPI = new DeleteAPI();

    String payloadAuth = "{ \"username\" : \"admin\", \"password\" : \"password123\" }";

    @Test
    public void deleteBooking() {
        Response authResponse = authAPI.createToken(payloadAuth);
        String token = authResponse.jsonPath().getString("token");

        String payloadBooking = JsonUtils.readJsonFile("src/test/resources/payloads/body_Post.json");
        Response bookingResponse = postAPI.createBooking(payloadBooking);
        int bookingId = bookingResponse.jsonPath().getInt("bookingid");
        assertEquals(200, bookingResponse.statusCode());

        Response deleteResponse = deleteAPI.deleteBooking(bookingId, token);
        assertEquals(201, deleteResponse.statusCode());
    }

    @Test
    public void deleteBookingWithoutAuth() {
        String payloadBooking = JsonUtils.readJsonFile("src/test/resources/payloads/body_Post.json");
        Response bookingResponse = postAPI.createBooking(payloadBooking);
        int bookingId = bookingResponse.jsonPath().getInt("bookingid");

        Response deleteResponse = deleteAPI.deleteBooking(bookingId, "");
        assertEquals(403, deleteResponse.statusCode());
    }

    @Test
    public void deleteBookingInvalidToken() {
        String payloadBooking = JsonUtils.readJsonFile("src/test/resources/payloads/body_Post.json");
        Response bookingResponse = postAPI.createBooking(payloadBooking);
        int bookingId = bookingResponse.jsonPath().getInt("bookingid");

        Response deleteResponse = deleteAPI.deleteBooking(bookingId, "tokenInvalido123");
        assertEquals(403, deleteResponse.statusCode());
    }
}