package ua.tqs.cito.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.tqs.cito.model.Product;
import ua.tqs.cito.service.AppService;
import ua.tqs.cito.service.ProductService;
import ua.tqs.cito.service.UserRegisterService;

@RestController
@Tag(name = "Manager", description = "the Manager API")
@RequestMapping("/managerApi")
public class ManagerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AppService appService;

    @Autowired
    private UserRegisterService userRegisterService;

    // Manager registers a product
    @Operation(summary = "Manager registers a product.")
    @PostMapping(value = "{managerId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerProduct(@PathVariable Long managerId, Long appid, @RequestBody Product p) {
        return productService.registerProduct(managerId, appid, p);
    }

    // Manager gets products of app
    @Operation(summary = "Manager gets all the products.")
    @GetMapping(value = "{managerId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllProducts(@PathVariable Long managerId, Long appid) {
        return productService.getAllProducts(managerId, appid);
    }

    // Manager registers his app
    @Operation(summary = "Register a app in the platform.")
    @PostMapping("{managerId}/app/register")
    public ResponseEntity<Object> registerApp(@PathVariable Long managerId,@RequestBody JsonNode payload) {
        return appService.registerApp(managerId, payload);
    }

    // Register a manager in the platform.
    @Operation(summary = "Register a manager in the platform.")
    @PostMapping(value="/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerManager(@RequestBody JsonNode payload){
        return userRegisterService.registerManager(payload);
    }

}
