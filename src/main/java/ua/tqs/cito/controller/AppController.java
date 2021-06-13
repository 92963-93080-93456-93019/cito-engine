package ua.tqs.cito.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.tqs.cito.service.OrderService;
import ua.tqs.cito.service.ProductService;
import ua.tqs.cito.service.SearchService;


@Controller
@RequestMapping("/clientApi")
public class AppController {

	@Autowired
	private SearchService searchService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	// Client searches for products
	@GetMapping(value="{consumerId}/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProductsByQuery(@PathVariable Long consumerId, Long appid, String query) {
		return searchService.getProductsByQuery(consumerId, appid, query);
	}

	// Client registers an order
	@PostMapping(value="{consumerId}/order/register",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registerOrder(@PathVariable Long consumerId, Long appid, @RequestBody JsonNode payload) {
		return orderService.registerOrder(consumerId, appid, payload);
	}

	// Client gets his orders
	@GetMapping(value= "{consumerId}/orders",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getOrders(@PathVariable Long consumerId, Long appid){
		return orderService.getOrders(consumerId, appid);
	}

	// Client gets all products of app
	@GetMapping(value="{consumerId}/products",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllProducts(@PathVariable Long consumerId, Long appid) {
		return productService.getAllProductsForClient(consumerId, appid);
	}

	// Client rates a Rider
	@PostMapping(value="{consumerId}/{riderId}/rate",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> rateRider(@PathVariable Long consumerId, @PathVariable Long riderId, Integer rating, Long appid){
		return orderService.consumerRatesRider(consumerId, riderId, rating, appid);
	}
}
