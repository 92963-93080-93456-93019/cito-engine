package ua.tqs.cito.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;

public class RiderControllerITest {

    @Test
    public void whenUpdatingOrder_thenReturnUpdated( ){

        String response = "{\"orderId\":2,\"status\":\"GOING_TO_BUY\"}";

        RestAssured
                .given()
                .contentType("application/json")
                .body(response).post("http://localhost:8081/riderApi/1/orders")
        .then()
                .assertThat()
                .statusCode(200)
                .and().body("message",equalTo("Order updated (GOING_TO_BUY)."));
    }

    @Test
    public void whenUpdatingOrderWithInvalidStatus_thenReturnForbidden( ) {

        String response = "{\"orderId\":2,\"status\":\"INVALID_STATUS\"}";

        RestAssured
                .given()
                .contentType("application/json")
                .body(response).post("http://localhost:8081/riderApi/1/orders")
        .then()
                .assertThat()
                .statusCode(403).and().body("message",equalTo("Status invalid."));
    }

    @Test
    public void whenUpdatingOrderWithInvalidRiderId_thenReturnForbidden( ) {

        String response = "{\"orderId\":2,\"status\":\"GOING_TO_BUY\"}";

        RestAssured
                .given()
                .contentType("application/json")
                .body(response).post("http://localhost:8081/riderApi/20/orders")
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
                .body(response).post("http://localhost:8081/riderApi/1/availability")
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
    
    @Test
    public void whenGetAvailability_thenReturnAvailability(){

        RestAssured
                .get("http://localhost:8081/riderApi/1/availability")
                .then()
                .assertThat()
                .and().statusCode(200)
                .and().body("$", hasKey("availability"));
    }
    
    @Test
    public void whenGetLocation_thenReturnLocation(){

        RestAssured
                .get("http://localhost:8081/riderApi/1/location")
                .then()
                .assertThat()
                .and().statusCode(200)
                .and().body("$", hasKey("longitude")).body("$", hasKey("latitude"));
    }
    
    @Test
    public void whenGetLocationForInvalidRider_thenReturnError(){

        RestAssured
                .get("http://localhost:8081/riderApi/100/location")
                .then()
                .assertThat()
                .and().statusCode(403)
                .and().body("message", equalTo("Invalid rider."));
    }
    
    @Test
    public void whenGetAvailabilityForInvalidRider_thenReturnError(){

        RestAssured
                .get("http://localhost:8081/riderApi/100/availability")
                .then()
                .assertThat()
                .and().statusCode(403)
                .and().body("message", equalTo("Invalid rider."));
    }
}
