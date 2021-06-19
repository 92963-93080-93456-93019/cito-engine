package ua.tqs.cito.integration;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.tqs.cito.controller.AppController;
import ua.tqs.cito.service.OrderService;
import ua.tqs.cito.service.ProductService;

@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class AppControllerITest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mockMvc(mvc);
    }

    @Test
    public void whenRegisterOrder_thenReturnCreated(){

        String response = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":5,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": 50.0}}";

        given()
                .contentType("application/json")
                .body(response).post("clientApi/1/orders?appid=1")
        .then()
                .assertThat()
                .statusCode(201);

    }

    @Test
    public void whenGetOrders_thenReturnOrders(){

        given()
                .get("clientApi/1/orders?appid=1")
                .then()
                .assertThat()
                .and().statusCode(200);
    }

    @Test
    public void whenGetProducts_thenReturnProductss(){

        given()
                .get("http://localhost:8081/clientApi/1/products?appid=1")
                .then()
                .assertThat()
                .and().statusCode(200)
                .and().body("size()",greaterThan(0));
    }

    @Test
    public void whenGetProductsByQuery_ReturnProducts(){

        given()
                .get("http://localhost:8081/clientApi/1/products?appid=1&query=benuron")
                .then()
                .assertThat()
                .and().statusCode(200)
                .and().body("size()",greaterThan(0));
    }

    @Test
    public void whenRateRider_ReturnOK(){

        given()
                .post("http://localhost:8081/clientApi/1/1/rate?rating=5&appid=1")
                .then()
                .assertThat()
                .and().statusCode(200);
    }


}
