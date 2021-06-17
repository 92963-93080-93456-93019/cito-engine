package ua.tqs.cito.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.tqs.cito.service.OrderService;
import ua.tqs.cito.service.RiderService;
import ua.tqs.cito.service.UserRegisterService;

@RestController
@Tag(name = "Rider", description = "the Rider API")
@RequestMapping("/riderApi")
public class RiderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private RiderService riderService;

    // Rider updates an order state
    @Operation(summary = "Rider updates an order status.")
    @PostMapping(value="{riderId}/orders",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateOrder(@PathVariable Long riderId, @RequestBody JsonNode payload){
        return orderService.updateOrder(riderId, payload);
    }

    // Rider gets all his orders
    @Operation(summary = "Rider gets all his orders.")
    @GetMapping(value="{riderId}/orders",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOrders(@PathVariable Long riderId){
        return orderService.getOrdersForRider(riderId);
    }

    // Register a rider in the platform.
    @Operation(summary = "Register a rider in the platform.")
    @PostMapping(value="/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerRider(@RequestBody JsonNode payload){
        return userRegisterService.registerRider(payload);
    }

    // Rider updates his availability
    @Operation(summary = "Rider updates his availability.")
    @PostMapping(value="{riderId}/availability",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerRider(@PathVariable Long riderId,@RequestBody JsonNode payload){
        return riderService.updateAvailability(payload,riderId);
    }

    // Rider updates his location
    @Operation(summary = "Rider updates his location.")
    @PostMapping(value="{riderId}/location",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateLocation(@PathVariable Long riderId,@RequestBody JsonNode payload){
        return riderService.updateLocation(payload,riderId);
    }
}
