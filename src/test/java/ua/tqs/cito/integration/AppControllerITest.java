package ua.tqs.cito.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.hamcrest.Matchers.*;

import ua.tqs.cito.CitoApplication;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CitoApplication.class)
@AutoConfigureMockMvc
public class AppControllerITest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    public void whenRegisterOrder_thenReturnCreated(){

        String response = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":5,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": 50.0}}";

        RestAssured
                .given()
                .contentType("application/json")
                .body(response).post("http://localhost:8081/clientApi/1/orders?appid=1")
                .then()
                .assertThat()
                .statusCode(201);

    }

    @Test
    public void whenGetOrders_thenReturnOrders(){

        RestAssured
                .get("http://localhost:8081/clientApi/1/orders?appid=1")
                .then()
                .assertThat()
                .and().statusCode(200)
                .and().body("size()",greaterThan(0));
    }

    @Test
    public void whenGetProducts_thenReturnProductss(){

        RestAssured
                .get("http://localhost:8081/clientApi/1/products?appid=1")
                .then()
                .assertThat()
                .and().statusCode(200)
                .and().body("size()",greaterThan(0));
    }

    @Test
    public void whenGetProductsByQuery_ReturnProducts(){

        RestAssured
                .get("http://localhost:8081/clientApi/1/products?appid=1&query=benuron")
                .then()
                .assertThat()
                .and().statusCode(200)
                .and().body("size()",greaterThan(0));
    }

    @Test
    public void whenRateRider_ReturnOK(){

        RestAssured
                .post("http://localhost:8081/clientApi/1/1/rate?rating=5&appid=1")
                .then()
                .assertThat()
                .and().statusCode(200);
    }


}
