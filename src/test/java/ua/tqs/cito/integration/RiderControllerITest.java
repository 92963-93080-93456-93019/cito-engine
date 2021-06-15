package ua.tqs.cito.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class RiderControllerITest {

    @Test
    public void whenUpdatingOrder_thenReturnUpdated( ){
        RestAssured
                .get("http://localhost:8081/riderApi/1/order/update?riderId=1&appid=1&orderId=4&status=GOING_TO_BUY")
        .then()
                .assertThat()
                .statusCode(200)
                .and().body("message",equalTo("Order updated (GOING_TO_BUY)."));
    }

    @Test
    public void whenGetingOrderWithInvalidApp_thenReturnForbidden( ) {

        RestAssured
                .get("http://localhost:8081/riderApi/1/order/update?riderId=1&appid=0&orderId=1&status=GOING_TO_BUY")
        .then()
                .assertThat()
                .statusCode(403).and().body("message",equalTo("Invalid appId."));
    }

    @Test
    public void whenUpdatingOrderWithInvalidRiderId_thenReturnForbidden( ) {
        RestAssured
                .get("http://localhost:8081/riderApi/20/order/update?riderId=20&appid=1&orderId=1&status=GOING_TO_BUY")
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

        RestAssured
                .given()
                .contentType("application/json")
                .body(response).post("http://localhost:8081/riderApi/register")
                .then()
                .assertThat()
                .statusCode(201);

    }

    @Test
    public void whenUpdatingRiderAvailability_thenReturnUpdated( )  {

        String response = "{\n" +
                "        \"availability\": \"True\" "+
                "}";

        RestAssured
                .given()
                .contentType("application/json")
                .body(response).post("http://localhost:8081/riderApi/1/available")
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

        RestAssured
                .given()
                .contentType("application/json")
                .body(response).post("http://localhost:8081/riderApi/1/location")
                .then()
                .assertThat()
                .statusCode(200)
                .and().body("message",equalTo("Rider location updated."));
    }
}
