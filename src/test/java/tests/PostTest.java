package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pages.PostAPI;
import utils.JsonUtils;
import org.json.JSONObject;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostTest {

    PostAPI postAPI = new PostAPI();

    private JSONObject getBasePayload() {
        String jsonString = JsonUtils.readJsonFile("src/test/resources/payloads/body_Post.json");
        return new JSONObject(jsonString);
    }

    @Test
    public void createBookingWithAllValidFields() {
        JSONObject payload = getBasePayload();
        Response response = postAPI.createBooking(payload.toString());
        response.then()
                .statusCode(200)
                .body("bookingid", notNullValue());
    }

    @Test
    public void createBookingWithoutFirstname() {
        JSONObject payload = getBasePayload();
        payload.remove("firstname");

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithInvalidFirstname() {
        JSONObject payload = getBasePayload();
        payload.put("firstname", 123);

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithoutLastname() {
        JSONObject payload = getBasePayload();
        payload.remove("lastname");

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithInvalidLastname() {
        JSONObject payload = getBasePayload();
        payload.put("lastname", JSONObject.NULL);

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithoutTotalprice() {
        JSONObject payload = getBasePayload();
        payload.remove("totalprice");

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithInvalidTotalprice() {
        JSONObject payload = getBasePayload();
        payload.put("totalprice", "free");

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithoutDepositpaid() {
        JSONObject payload = getBasePayload();
        payload.remove("depositpaid");

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithInvalidDepositpaid() {
        JSONObject payload = getBasePayload();
        payload.put("depositpaid", "yes");

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithoutCheckinDate() {
        JSONObject payload = getBasePayload();
        payload.getJSONObject("bookingdates").remove("checkin");

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithInvalidCheckin() {
        JSONObject payload = getBasePayload();
        payload.getJSONObject("bookingdates").put("checkin", "123");

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithoutCheckoutDate() {
        JSONObject payload = getBasePayload();
        payload.getJSONObject("bookingdates").remove("checkout");

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithInvalidCheckout() {
        JSONObject payload = getBasePayload();
        payload.getJSONObject("bookingdates").put("checkout", "20230110");

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }

    @Test
    public void createBookingWithCheckoutEarlierThanCheckin() {
        JSONObject payload = getBasePayload();
        payload.getJSONObject("bookingdates").put("checkin", "2023-01-10");
        payload.getJSONObject("bookingdates").put("checkout", "2023-01-01");

        Response response = postAPI.createBooking(payload.toString());
        response.then().statusCode(500).body(equalTo("Internal Server Error"));
    }
}