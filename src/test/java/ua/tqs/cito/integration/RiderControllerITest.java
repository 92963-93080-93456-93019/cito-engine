package ua.tqs.cito.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import static org.hamcrest.Matchers.equalTo;

@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class RiderControllerITest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mockMvc(mvc);
    }

    @Test
    public void whenUpdatingOrder_thenReturnUpdated( ){

        String response = "{\"orderId\":2,\"status\":\"GOING_TO_BUY\"}";

        given()
                .contentType("application/json")
                .body(response).post("riderApi/1/orders")
        .then()
                .assertThat()
                .statusCode(200)
                .and().body("message",equalTo("Order updated (GOING_TO_BUY)."));
    }

    @Test
    public void whenUpdatingOrderWithInvalidStatus_thenReturnForbidden( ) {

        String response = "{\"orderId\":2,\"status\":\"INVALID_STATUS\"}";

        given()
                .contentType("application/json")
                .body(response).post("riderApi/1/orders")
        .then()
                .assertThat()
                .statusCode(403).and().body("message",equalTo("Status invalid."));
    }

    @Test
    public void whenUpdatingOrderWithInvalidRiderId_thenReturnForbidden( ) {

        String response = "{\"orderId\":2,\"status\":\"GOING_TO_BUY\"}";

        given()
                .contentType("application/json")
                .body(response).post("riderApi/20/orders")
        .then()
                .assertThat()
                .statusCode(403)
                .and().body("message",equalTo("Invalid rider."));
    }

    @Test
    public void whenRegisterRider_thenReturnCreated() {

        String response = "{\n" +
                "    \"vehicle\":{\n" +
                "        \"name\": \"Mercedes\",\n" +
                "        \"license\": \"00-00-00\"\n" +
                "    },\n" +
                "    \"rider\":{\n" +
                "        \"fname\": \"Dinis\",\n" +
                "        \"lname\": \"Cruz\",\n" +
                "        \"fnumber\": \"919191781\"\n" +
                "    }\n" +
                "\n" +
                "}";

        given()
                .contentType("application/json")
                .body(response).post("riderApi/register")
        .then()
                .assertThat()
                .statusCode(201);

    }

    @Test
    public void whenUpdatingRiderAvailability_thenReturnUpdated( )  {

        String response = "{\n" +
                "        \"availability\": \"True\" "+
                "}";

        given()
                .contentType("application/json")
                .body(response).post("riderApi/1/availability")
        .then()
                .assertThat()
                .statusCode(200)
                .and().body("message",equalTo("Rider availability updated."));
    }

    @Test
    public void whenUpdatingRiderLocation_thenReturnUpdated( )  {

        String response = "{\n" +
                "        \"latitude\": \"50.0\" ,"+
                "        \"longitude\": \"50.0\" "+
                "}";

        given()
                .contentType("application/json")
                .body(response).post("riderApi/1/location")
        .then()
                .assertThat()
                .statusCode(200)
                .and().body("message",equalTo("Rider location updated."));
    }
}
