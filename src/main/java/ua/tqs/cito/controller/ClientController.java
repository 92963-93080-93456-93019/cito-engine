package ua.tqs.cito.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.tqs.cito.model.Order;
import ua.tqs.cito.service.OrderService;
import ua.tqs.cito.service.ProductService;
import ua.tqs.cito.service.SearchService;


@Controller
@RequestMapping("/clientApi")
public class ClientController {

	@Autowired
	private SearchService searchService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	// Client searches for products
	@GetMapping(value="{clientId}/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProductsByQuery(@PathVariable Long clientId, Long appid, String query) {
		return searchService.getProductsByQuery(clientId, appid, query);
    }

    // Client registers an order
	@PostMapping(value="{clientId}/order/register",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registerOrder(@PathVariable Long clientId, Long appid, @RequestBody JsonNode payload) {
		System.out.println("Cheguei ao controller");
		return orderService.registerOrder(clientId, appid, payload);
	}

	// Client gets his orders
	@GetMapping(value="{clientId}/orders",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getOrders(@PathVariable Long clientId, Long appid){
		return orderService.getOrders(clientId, appid);
	}

	// Client gets all products of app
	@GetMapping(value="{clientId}/products",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllProducts(@PathVariable Long clientId, Long appid) {
		return productService.getAllProductsForClient(clientId, appid);
	}

	// rate rider
	@PostMapping(value="/rate",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> rateRider(@PathVariable Long riderId,Integer rating){
		return orderService.rate(riderId,rating);
	}
}
