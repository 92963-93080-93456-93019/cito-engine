package ua.tqs.cito.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tqs.cito.model.Product;
import ua.tqs.cito.service.AppService;
import ua.tqs.cito.service.ProductService;
import ua.tqs.cito.service.UserService;

@RestController
@Tag(name = "Manager", description = "the Manager API")
@RequestMapping("/managerApi")
@CrossOrigin(origins = "https://cito-manager-app.herokuapp.com")
public class ManagerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AppService appService;

    @Autowired
    private UserService userService;

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
        return userService.registerManager(payload);
    }

    // Manager retrieves his info
    @Operation(summary = "Retrieve manager info.")
    @GetMapping(value="/{managerId}/info",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getManagerInfo(@PathVariable Long managerId){
        return userService.getManager(managerId);
    }

    // Manager gets app statistics
    @Operation(summary = "Manager gets app statistics.")
    @GetMapping(value="/{appid}/statistics",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getStatistics(@PathVariable Long appid){
        return appService.getStatistics(appid);
    }


}
