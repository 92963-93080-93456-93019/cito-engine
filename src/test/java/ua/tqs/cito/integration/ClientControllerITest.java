package ua.tqs.cito.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.aspectj.lang.annotation.Before;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import ua.tqs.cito.CitoApplication;
import ua.tqs.cito.controller.ClientController;
import ua.tqs.cito.model.*;
import ua.tqs.cito.repository.AppRepository;
import ua.tqs.cito.repository.ConsumerRepository;
import ua.tqs.cito.repository.ProductRepository;
import ua.tqs.cito.service.OrderService;
import ua.tqs.cito.utils.HttpResponses;
import ua.tqs.cito.utils.OrderStatusEnum;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CitoApplication.class)
@AutoConfigureMockMvc
public class ClientControllerITest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    /*@Test
    public void whenRegisterOrder_thenReturnCreated() throws Exception {

        String response = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":5,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true}}";

        RestAssured
                .given()
                .contentType("application/json")
                .body(response).post("http://localhost:8081/clientApi/1/order/register?appid=1")
                .then()
                .assertThat()
                .statusCode(201);

    }

    @Test
    public void whenGetOrders_thenReturnOrders() throws Exception {

        RestAssured
                .get("http://localhost:8081/clientApi/1/orders?clientId=1&appid=1")
                .then()
                .assertThat()
                .and().statusCode(200)
                .and().body("size()",greaterThan(0));
    }

    @Test
    public void whenGetProducts_thenReturnProductss() throws Exception {

        RestAssured
                .get("http://localhost:8081/clientApi/1/products?clientId=1&appid=1")
                .then()
                .assertThat()
                .and().statusCode(200)
                .and().body("size()",greaterThan(0));
    }

    @Test
    public void whenGetProductsByQuery_ReturnProducts() throws Exception {

        RestAssured
                .get("http://localhost:8081/clientApi/1/search?clientId=1&appid=1&query=benuron")
                .then()
                .assertThat()
                .and().statusCode(200)
                .and().body("size()",greaterThan(0));
    }*/

}
