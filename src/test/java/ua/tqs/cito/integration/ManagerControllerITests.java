package ua.tqs.cito.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import ua.tqs.cito.controller.ClientController;
import ua.tqs.cito.model.App;
import ua.tqs.cito.model.Product;
import ua.tqs.cito.service.ProductService;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

@WebMvcTest(ClientController.class)
public class ManagerControllerITests {
	
		@Autowired
	    private MockMvc mvc;

	    @MockBean
	    private ProductService productService;

		private static final String PRODUCT_SAVED = "{\"code\" : 201, \"message\" : \"Product saved.\"}";
	    
	    /*@Test
	    public void whenPostInProduct_thenReturnCreatedResponse( ) throws Exception {
	    	App app1 = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
	    	Product p = new Product("Benuron","Farmácia Geral","Great for small pains!",app1,13.00,"somebase64string");
	    	
	    	RestAssuredMockMvc.mockMvc(mvc);

	        when( productService.registerProduct(any(), any(), any())).thenReturn( new ResponseEntity<>(PRODUCT_SAVED, HttpStatus.CREATED));

	        RestAssuredMockMvc
					.given()
						.header("Content-type","application/json")
						.body("{\"name\":\"Benuron\",\"category\":\"Farmácia Geral\",\"description\":\"Great for small pains!\",\"appId\":1,\"price\":13.00,\"image\":\"somebase64string\"}")
						.post("http://localhost:8000/product/register")
					.then()
						.statusCode(201)
						.body(equalTo(PRODUCT_SAVED));
	    }
	    
	    @Test
	    public void whenPostInvalidProductReturnBadRequest( ) throws Exception {
	    	
	    	RestAssuredMockMvc.mockMvc(mvc);

	        when( productService.registerProduct(any(), any(), any()) ).thenReturn(null);

	        RestAssuredMockMvc
					.given()
						.header("Content-type","application/json")
						.body("{\"name\":null,\"category\":\"Farmácia Geral\",\"description\":null,\"appId\":1,\"price\":13.00,\"image\":\"somebase64string\"}")
						.post("http://localhost:8000/product/register")
					.then()
						.statusCode(400);
	    }*/

}
