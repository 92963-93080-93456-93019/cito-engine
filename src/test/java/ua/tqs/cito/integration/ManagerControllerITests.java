package ua.tqs.cito.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import ua.tqs.cito.CitoApplication;
import ua.tqs.cito.controller.AppController;
import ua.tqs.cito.model.App;
import ua.tqs.cito.model.Product;
import ua.tqs.cito.service.ProductService;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.restassured.RestAssured;
import ua.tqs.cito.utils.HttpResponses;

import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CitoApplication.class)
@AutoConfigureMockMvc
public class ManagerControllerITests {

	@LocalServerPort
	int port;

	@BeforeEach
	void setup() {
		RestAssured.port = port;
	}
	    
	@Test
	public void whenPostInProduct_thenReturnCreatedResponse( ) {

		Random ran = new Random();
		int x = ran.nextInt(999) + 1000;
		String prodName = "TestProduct" + x;

		String request = "{\"name\":\""+prodName+"\",\"category\":\"Farmácia Geral\",\"description\":\"Great for small pains!\",\"appId\":1,\"price\":13.00,\"image\":\"somebase64string\"}";

		RestAssured
				.given()
					.contentType("application/json")
					.body(request).post("http://localhost:8081/managerApi/1/products?appid=1")
				.then()
					.statusCode(201)
					.body(equalTo(HttpResponses.PRODUCT_SAVED));
	}

	@Test
	public void whenPostInvalidProduct_thenReturnBadRequest( ) throws Exception {


		Random ran = new Random();
		int x = ran.nextInt(999) + 1000;
		String prodName = "TestProduct" + x;

		String request = "{\"name\":\""+prodName+"\",\"category\":\"Farmácia Geral\",\"description\":\"Great for small pains!\",\"appId\":1,\"image\":\"somebase64string\"}";

		RestAssured
				.given()
					.contentType("application/json")
					.body(request).post("http://localhost:8081/managerApi/1/products?appid=1")
				.then()
					.statusCode(500)
					.body(equalTo(HttpResponses.PRODUCT_NOT_SAVED));
	}

	@Test
	public void whenRegisterAppthenReturnCreated() {

		String request = "{\n" +
				"    \"tax\":50,\n" +
				"    \"name\": \"appfixe\",\n" +
				"    \"address\": \"Rua fixe\",\n" +
				"    \"schedule\": \"24/7\",\n" +
				"    \"image\":\"imagemfixe\"\n" +
				"}";

		RestAssured
				.given()
				.contentType("application/json")
				.body(request).post("http://localhost:8081/managerApi/1/app/register")
				.then()
				.assertThat()
				.statusCode(201);

	}

}
