package ua.tqs.cito.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class RiderControllerITest {

    /*@Test
    public void whenUpdatingOrder_thenReturnUpdated( ) throws Exception {
        RestAssured
                .get("http://localhost:8081/riderApi/1/order/update?riderId=1&appid=1&orderId=1&status=GOING_TO_BUY")
        .then()
                .assertThat()
                .statusCode(200)
                .and().body("message",equalTo("Order updated (GOING_TO_BUY)."));
    }

    @Test
    public void whenGetingOrderWithInvalidApp_thenReturnForbidden( ) throws Exception {

        RestAssured
                .get("http://localhost:8081/riderApi/1/order/update?riderId=1&appid=0&orderId=1&status=GOING_TO_BUY")
        .then()
                .assertThat()
                .statusCode(403).and().body("message",equalTo("Invalid appId."));
    }

    @Test
    public void whenUpdatingOrderWithInvalidRiderId_thenReturnForbidden( ) throws Exception {
        RestAssured
                .get("http://localhost:8081/riderApi/20/order/update?riderId=20&appid=1&orderId=1&status=GOING_TO_BUY")
        .then()
                .assertThat()
                .statusCode(403)
                .and().body("message",equalTo("Invalid rider."));
    }*/

}
