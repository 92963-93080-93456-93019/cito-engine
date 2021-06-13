package ua.tqs.cito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.tqs.cito.model.Product;
import ua.tqs.cito.service.ProductService;

@Controller
@Tag(name = "Manager", description = "the Manager API")
@RequestMapping("/managerApi")
public class ManagerController {

    @Autowired
    private ProductService productService;

    // Manager registers a product
    @Operation(summary = "Manager registers a product.")
    @PostMapping("{managerId}/product/register")
    public ResponseEntity<Object> registerProduct(@PathVariable Long managerId, Long appid, @RequestBody Product p) {
        return productService.registerProduct(managerId, appid, p);
    }

    // Manager gets products of app
    @Operation(summary = "Shows all products.")
    @GetMapping("{managerId}/products")
    public ResponseEntity<Object> getAllProducts(@PathVariable Long managerId, Long appid) {
        return productService.getAllProducts(managerId, appid);
    }

}
