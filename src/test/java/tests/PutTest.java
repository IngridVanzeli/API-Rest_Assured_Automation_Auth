package tests;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import pages.AuthAPI;
import pages.PostAPI;
import pages.PutAPI;
import utils.JsonUtils;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PutTest {

    AuthAPI authAPI = new AuthAPI();
    PostAPI postAPI = new PostAPI();
    PutAPI putAPI = new PutAPI();

    private JSONObject getBasePayload() {
        String jsonString = JsonUtils.readJsonFile("src/test/resources/payloads/body_Update.json");
        return new JSONObject(jsonString);
    }

    private String getToken() {
        String payloadAuth = "{ \"username\" : \"admin\", \"password\" : \"password123\" }";
        Response authResponse = authAPI.createToken(payloadAuth);
        return authResponse.jsonPath().getString("token");
    }

    private int createBooking() {
        String payloadBooking = JsonUtils.readJsonFile("src/test/resources/payloads/body_Post.json");
        Response bookingResponse = postAPI.createBooking(payloadBooking);
        return bookingResponse.jsonPath().getInt("bookingid");
    }

    @Test
    public void updateBookingWithAllValidFields() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        Response response = putAPI.updateBooking(bookingId, token, payload.toString());

        response.then().statusCode(200)
                .body("firstname", equalTo(payload.getString("firstname")))
                .body("lastname", equalTo(payload.getString("lastname")))
                .body("totalprice", equalTo(payload.getInt("totalprice")))
                .body("depositpaid", equalTo(payload.getBoolean("depositpaid")))
                .body("bookingdates.checkin", equalTo(payload.getJSONObject("bookingdates").getString("checkin")))
                .body("bookingdates.checkout", equalTo(payload.getJSONObject("bookingdates").getString("checkout")))
                .body("additionalneeds", equalTo(payload.getString("additionalneeds")));
    }

    @Test
    public void updateBookingWithoutFirstname() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.remove("firstname");

        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }

    @Test
    public void updateBookingWithInvalidFirstname() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.put("firstname", JSONObject.NULL);

        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }

    @Test
    public void updateBookingWithoutLastname() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.remove("lastname");

        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }

    @Test
    public void updateBookingWithInvalidLastname() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.put("lastname", JSONObject.NULL);

        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }

    @Test
    public void updateBookingWithoutTotalprice() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.remove("totalprice");

        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }

    @Test
    public void updateBookingWithInvalidTotalprice() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.put("totalprice", "free");

        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }

    @Test
    public void updateBookingWithoutDepositpaid() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.remove("depositpaid");

        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }


    @Test
    public void updateBookingWithoutCheckin() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.getJSONObject("bookingdates").remove("checkin");

        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }

    @Test
    public void updateBookingWithInvalidCheckin() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.getJSONObject("bookingdates").put("checkin", "123"); // inválido

        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }


    @Test
    public void updateBookingWithoutCheckout() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.getJSONObject("bookingdates").remove("checkout");

        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }

    @Test
    public void updateBookingWithInvalidCheckout() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.getJSONObject("bookingdates").put("checkout", "123");

        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }

    @Test
    public void updateBookingWithCheckoutEarlierThanCheckin() {
        String token = getToken();
        int bookingId = createBooking();

        JSONObject payload = getBasePayload();
        payload.getJSONObject("bookingdates").put("checkin", "2025-02-20");
        payload.getJSONObject("bookingdates").put("checkout", "2025-02-01");
        Response response = putAPI.updateBooking(bookingId, token, payload.toString());
        response.then().statusCode(400).body(equalTo("Bad Request"));
    }
}