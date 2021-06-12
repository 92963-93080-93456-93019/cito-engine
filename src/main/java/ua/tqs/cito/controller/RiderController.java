package ua.tqs.cito.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.tqs.cito.service.OrderService;
import ua.tqs.cito.service.UserRegisterService;

@Controller
@RequestMapping("/riderApi")
public class RiderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRegisterService riderService;

    // Rider updates order state
    @GetMapping(value="{riderId}/order/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateOrder(@PathVariable Long riderId, Long appid, Long orderId, String status){
        return orderService.updateOrder(riderId, appid, orderId, status);
    }
    // Client registers an order
    @PostMapping(value="{clientId}/order/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerOrder(@PathVariable Long clientId, Long appid, @RequestBody JsonNode payload) {
        System.out.println("Cheguei ao controller");
        return orderService.registerOrder(clientId, appid, payload);
    }
    // Rider registers
    @PostMapping(value="/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerRider(@RequestBody JsonNode payload){
        return riderService.register(payload);
    }
}
