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
import ua.tqs.cito.service.UserRegisterService;

@Controller
@Tag(name = "Rider", description = "the Rider API")
@RequestMapping("/riderApi")
public class RiderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRegisterService userRegisterService;

    // Rider updates order state
    @Operation(summary = "Update order status.")
    @GetMapping(value="{riderId}/order/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateOrder(@PathVariable Long riderId, Long appid, Long orderId, String status){
        return orderService.updateOrder(riderId, appid, orderId, status);
    }
    // Client registers an order
    @Operation(summary = "Register an order by a client.")
    @PostMapping(value="{clientId}/order/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerOrder(@PathVariable Long clientId, Long appid, @RequestBody JsonNode payload) {
        System.out.println("Cheguei ao controller");
        return orderService.registerOrder(clientId, appid, payload);
    }
    // Rider registers
    @Operation(summary = "Register a rider in the platform.")
    @PostMapping(value="/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerRider(@RequestBody JsonNode payload){
        return userRegisterService.registerRider(payload);
    }
}
