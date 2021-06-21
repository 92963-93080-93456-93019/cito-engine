package ua.tqs.cito.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tqs.cito.service.OrderService;
import ua.tqs.cito.service.ProductService;


@RestController
@Tag(name = "Client", description = "the client API")
@RequestMapping("/clientApi")
@CrossOrigin(origins = "*")
public class AppController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	// Client registers an order
	@Operation(summary = "Register an order.")
	@PostMapping(value="{consumerId}/orders",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registerOrder(@PathVariable Long consumerId, Long appid, @RequestBody JsonNode payload) {
		return orderService.registerOrder(consumerId, appid, payload);
	}

	// Client gets his orders
	@Operation(summary = "Show orders of a consumer.")
	@GetMapping(value= "{consumerId}/orders",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getOrders(@PathVariable Long consumerId, Long appid){
		return orderService.getOrdersForConsumer(consumerId, appid);
	}

	// Client gets all products of app or filtered by search query by name
	@Operation(summary = "Show products of a consumer.")
	@GetMapping(value="{consumerId}/products",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllProductsForClient(@PathVariable Long consumerId, Long appid, String query) {
		return productService.getAllProductsForClient(consumerId, appid, query);
	}

	// Client rates a Rider
	@Operation(summary = "Consumer rates a rider.")
	@PostMapping(value="{consumerId}/{riderId}/rate",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> rateRider(@PathVariable Long consumerId, @PathVariable Long riderId, Integer rating, Long appid){
		return orderService.consumerRatesRider(consumerId, riderId, rating, appid);
	}

}
