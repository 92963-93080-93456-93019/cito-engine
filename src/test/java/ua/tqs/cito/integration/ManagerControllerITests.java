package ua.tqs.cito.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import ua.tqs.cito.utils.HttpResponses;

@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class ManagerControllerITests {

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	void setup() {
		mockMvc(mvc);
	}
	    
	@Test
	public void whenPostInProduct_thenReturnCreatedResponse( ) {

		String request = "{\"name\":\"Batata\",\"category\":\"Farmácia Geral\",\"description\":\"Great for small pains!\",\"appId\":1,\"price\":13.00,\"image\":\"somebase64string\"}";

		given()
			.contentType("application/json")
			.body(request).post("managerApi/1/products?appid=1")
		.then()
			.statusCode(201)
			.body(equalTo(HttpResponses.PRODUCT_SAVED));
	}

	@Test
	public void whenPostInvalidProduct_thenReturnBadRequest( ) throws Exception {

		String request = "{\"name\":\"Batata\",\"category\":\"Farmácia Geral\",\"description\":\"Great for small pains!\",\"appId\":1,\"image\":\"somebase64string\"}";

		given()
			.contentType("application/json")
			.body(request).post("managerApi/1/products?appid=1")
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


		given()
				.contentType("application/json")
				.body(request).post("managerApi/1/app/register")
		.then()
				.assertThat()
				.statusCode(201);

	}

	@Test
	public void whenRegisterManagerReturnCreated() {

		String request = "{\n" +
				"    \"fname\":\"Tiago\",\n" +
				"    \"lname\": \"Oliveira\",\n" +
				"    \"address\": \"Rua fixe\",\n" +
				"    \"phone\": \"919191785\"\n" +
				"}";

		given()
				.contentType("application/json")
				.body(request).post("managerApi/register")
		.then()
				.assertThat()
				.statusCode(201);

	}

	@Test
	public void whenGetRevenueReturnRevenue() {

		given()
				.get("managerApi/1/revenue")
		.then()
				.assertThat()
				.and().statusCode(200);

	}

}
