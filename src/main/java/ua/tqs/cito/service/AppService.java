package ua.tqs.cito.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.tqs.cito.model.*;
import ua.tqs.cito.repository.AppRepository;
import ua.tqs.cito.repository.ManagerRepository;
import ua.tqs.cito.repository.OrderRepository;
import ua.tqs.cito.repository.ProductRepository;
import ua.tqs.cito.utils.HttpResponses;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ProductRepository productRepository;

    ObjectMapper mapper = new ObjectMapper();

    public ResponseEntity<Object> registerApp(Long managerId, JsonNode payload ){

        if (!checkManagerId(managerId))
            return new ResponseEntity<>(HttpResponses.INVALID_MANAGER, HttpStatus.FORBIDDEN);

        double tax = payload.path("tax").asDouble();

        if(tax==0)
            return new ResponseEntity<>(HttpResponses.INVALID_TAX, HttpStatus.FORBIDDEN);

        String name = payload.path("name").asText();

        if(name.equals(""))
            return new ResponseEntity<>(HttpResponses.INVALID_NAME, HttpStatus.FORBIDDEN);

        String address = payload.path("address").asText();

        if(address.equals(""))
            return new ResponseEntity<>(HttpResponses.INVALID_APP_ADDRESS, HttpStatus.FORBIDDEN);

        String image = payload.path("image").asText();

        if(image.equals(""))
            return new ResponseEntity<>(HttpResponses.INVALID_IMAGE, HttpStatus.FORBIDDEN);

        String schedule = payload.path("schedule").asText();

        if(schedule.equals(""))
            return new ResponseEntity<>(HttpResponses.INVALID_SCHEDULE, HttpStatus.FORBIDDEN);

        App app = new App(1L,tax,name,address,schedule,image);
        Manager manager = managerRepository.findByManagerId(managerId);
        manager.setApp(app);
        appRepository.save(app);
        return new ResponseEntity<>(HttpResponses.APP_CREATED, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getStatistics(Long appid) {

        // Total Revenue

        ObjectNode revenueObj = mapper.createObjectNode();

        double revenue = 0;

        if (checkAppId(appid))
            return new ResponseEntity<>(HttpResponses.INVALID_APP, HttpStatus.FORBIDDEN);

        App app = appRepository.findByAppid(appid);

        List<Order> orders = orderRepository.findOrdersByApp(app);

        int amountOfOrders = orders.size();

        if(amountOfOrders!=0){
            for(Order o:orders){
                revenue = revenue + o.getPrice();
            }
        }

        revenueObj.put("name","Total Revenue");
        revenueObj.put("description","Sum of all orders income.");
        revenueObj.put("type","currency");
        revenueObj.put("value",revenue);

        // Total amount of orders

        ObjectNode amountOrdersObj = mapper.createObjectNode();
        amountOrdersObj.put("name","Amount of Orders");
        amountOrdersObj.put("description","All the orders done through this app.");
        amountOrdersObj.put("type","count");
        amountOrdersObj.put("value",amountOfOrders);

        // Total amount of different clients

        ObjectNode distinctConsumersOfAppObj = mapper.createObjectNode();

        List<Consumer> distinctConsumersOfApp = orderRepository.findNumberOfDifferentClients(app);

        distinctConsumersOfAppObj.put("name","Number of clients");
        distinctConsumersOfAppObj.put("description","Total number of different clients.");
        distinctConsumersOfAppObj.put("type","count");
        distinctConsumersOfAppObj.put("value",distinctConsumersOfApp.size());

        // Total amount of different products

        ObjectNode productsOfAppObj = mapper.createObjectNode();

        List<Product> productsOfApp = productRepository.findByApp(app);

        productsOfAppObj.put("name","Number of products");
        productsOfAppObj.put("description","Total number of different products.");
        productsOfAppObj.put("type","count");
        productsOfAppObj.put("value", productsOfApp.size());

        // add JSON objects to array

        // Top sold products

        List<ProductSalesDTO> topSoldProducts = productRepository.findMostSoldProductsOfApp(app);
        Collections.sort(topSoldProducts);

        ArrayNode topProductsArr = mapper.valueToTree(topSoldProducts);

        ObjectNode topSoldProductsObj = mapper.createObjectNode();

        topSoldProductsObj.put("name","Top Sold Products");
        topSoldProductsObj.put("description","Top Sold products");
        topSoldProductsObj.put("type","topSold");
        topSoldProductsObj.set("value", topProductsArr);

        // create ArrayNode object
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.addAll(Arrays.asList(revenueObj, amountOrdersObj, distinctConsumersOfAppObj,productsOfAppObj, topSoldProductsObj));

        return new ResponseEntity<>(arrayNode, HttpStatus.OK);
    }

    // Check if manager exists
    private boolean checkManagerId(Long managerId) {
        return managerRepository.findByManagerId(managerId) != null;
    }

    // Check if app exists
    private boolean checkAppId(Long appId) {
        return appRepository.findByAppid(appId) == null;
    }
}
