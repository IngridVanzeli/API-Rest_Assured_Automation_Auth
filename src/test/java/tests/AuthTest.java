package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pages.AuthAPI;
import utils.PayloadUtils;

import static org.hamcrest.Matchers.notNullValue;

public class AuthTest {

    AuthAPI authAPI = new AuthAPI();

    @Test
    public void testCreateToken() {
        String payload = PayloadUtils.getPayload("auth.json");

        Response response = authAPI.createToken(payload);

        // Validações
        response.then()
                .statusCode(200)
                .body("token", notNullValue()) // garante que o token foi gerado
                .log().all();
    }
}