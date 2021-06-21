package ua.tqs.cito.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.tqs.cito.model.*;
import ua.tqs.cito.repository.*;
import ua.tqs.cito.utils.HttpResponses;
import ua.tqs.cito.utils.OrderStatusEnum;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private RiderRepository riderRepository;

    public ResponseEntity<Object> getOrdersForConsumer(Long clientId, Long appid) {

        if (checkAppId(appid))
            return new ResponseEntity<>(HttpResponses.INVALID_APP, HttpStatus.FORBIDDEN);

        if (checkConsumerId(clientId)) {
            return new ResponseEntity<>(HttpResponses.INVALID_CONSUMER, HttpStatus.FORBIDDEN);
        }

        Consumer c = consumerRepository.findByConsumerId(clientId);

        List<Order> orders = orderRepository.findOrdersByEndConsumer(c);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<Object> getOrdersForRider(Long riderId) {

        if (checkRiderId(riderId)) {
            return new ResponseEntity<>(HttpResponses.INVALID_RIDER, HttpStatus.FORBIDDEN);
        }

        Rider rider = riderRepository.findByRiderId(riderId);

        List<Order> orders = orderRepository.findOrdersByRider(rider);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<Object> registerOrder( Long clientId, Long appid, JsonNode payload ){

        if (checkAppId(appid))
            return new ResponseEntity<>(HttpResponses.INVALID_APP, HttpStatus.FORBIDDEN);

        App app = appRepository.findByAppid(appid);

        if (checkConsumerId(clientId)) {
            return new ResponseEntity<>(HttpResponses.INVALID_CONSUMER, HttpStatus.FORBIDDEN);
        }

        Consumer c = consumerRepository.findByConsumerId(clientId);

        List<ProductListItem> prods = new ArrayList<>();
        JsonNode payload_prods = payload.path("products");

        if(payload_prods.size()==0){
            return new ResponseEntity<>(HttpResponses.INSUFFICIENT_PRODUCTS, HttpStatus.FORBIDDEN);
        }


        for(JsonNode j:payload_prods){
            Long prodId = Long.parseLong(j.path("id").asText());
            Product p = checkAndGetProduct(prodId);
            if( p != null ){
                ProductListItem pli = new ProductListItem(p, j.path("quantity").asInt());
                prods.add(pli);
            }
            else{
                return new ResponseEntity<>(HttpResponses.INVALID_PRODUCT.replace("#", prodId.toString()), HttpStatus.FORBIDDEN);}
        }

        String address = payload.path("info").path("deliveryAddress").asText();
        if(address.equals(""))
            return new ResponseEntity<>(HttpResponses.INVALID_ADDRESS, HttpStatus.FORBIDDEN);
        if(payload.path("info").path("latitude").asText().equals(""))
            return new ResponseEntity<>(HttpResponses.ORDER_LOCATION_INVALID, HttpStatus.FORBIDDEN);

        double latitude = Double.parseDouble(payload.path("info").path("latitude").asText());

        if(latitude>90 || latitude <-90)
            return new ResponseEntity<>(HttpResponses.ORDER_LOCATION_INVALID, HttpStatus.FORBIDDEN);

        if(payload.path("info").path("longitude").asText().equals(""))
            return new ResponseEntity<>(HttpResponses.ORDER_LOCATION_INVALID, HttpStatus.FORBIDDEN);

        double longitude = Double.parseDouble(payload.path("info").path("longitude").asText());

        if(longitude>180 || longitude <-180)
            return new ResponseEntity<>(HttpResponses.ORDER_LOCATION_INVALID, HttpStatus.FORBIDDEN);

        Rider r1 = matchRider(latitude,longitude);

        if(r1==null)
            return new ResponseEntity<>(HttpResponses.NO_RIDERS_AVAILABLE, HttpStatus.NOT_FOUND);

        Order o1 = new Order(prods,c, OrderStatusEnum.PENDING,app,address,latitude,longitude);
        o1.setRider(r1);
        orderRepository.save(o1);
        return new ResponseEntity<>(HttpResponses.ORDER_SAVED, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateOrder(Long riderId, JsonNode payload) {

        if (checkRiderId(riderId))
            return new ResponseEntity<>(HttpResponses.INVALID_RIDER, HttpStatus.FORBIDDEN);

        long orderId = payload.path("orderId").asLong();
        String status = payload.path("status").asText();

        if(orderId == 0)
            return new ResponseEntity<>(HttpResponses.INVALID_ORDER, HttpStatus.FORBIDDEN);

        if(status.equals(""))
            return new ResponseEntity<>(HttpResponses.INVALID_STATUS, HttpStatus.FORBIDDEN);

        Order orderToUpdate = checkAndGetOrder(orderId);
        if( orderToUpdate == null ){
            return new ResponseEntity<>(HttpResponses.INVALID_ORDER, HttpStatus.FORBIDDEN);
        }

        String state = "";

        switch(status){
            case "GOING_TO_BUY":
                orderToUpdate.setOrderStatusEnum(OrderStatusEnum.GOING_TO_BUY);
                state="GOING_TO_BUY";
                break;
            case "BUYING":
                orderToUpdate.setOrderStatusEnum(OrderStatusEnum.BUYING);
                state="BUYING";
                break;
            case "DELIVERING":
                orderToUpdate.setOrderStatusEnum(OrderStatusEnum.DELIVERING);
                state="DELIVERING";
                break;
            case "DELIVERED":
                orderToUpdate.setOrderStatusEnum(OrderStatusEnum.DELIVERED);
                state="DELIVERED";
                break;
            default:
                return new ResponseEntity<>(HttpResponses.INVALID_STATUS, HttpStatus.FORBIDDEN);
        }

        orderRepository.save(orderToUpdate);
        return new ResponseEntity<>(HttpResponses.ORDER_UPDATED.replace("#", state), HttpStatus.OK);
    }

    public ResponseEntity<Object> consumerRatesRider(Long consumerId, Long riderId, Integer rating, Long appid) {
        if (checkAppId(appid))
            return new ResponseEntity<>(HttpResponses.INVALID_APP, HttpStatus.FORBIDDEN);

        if (checkConsumerId(consumerId))
            return new ResponseEntity<>(HttpResponses.INVALID_CONSUMER, HttpStatus.FORBIDDEN);

        if (checkRiderId(riderId))
            return new ResponseEntity<>(HttpResponses.INVALID_RIDER, HttpStatus.FORBIDDEN);

        Rider r1 = riderRepository.findByRiderId(riderId);

        r1.addRep(rating);

        return new ResponseEntity<>(HttpResponses.RIDER_RATED, HttpStatus.OK);
    }



    // Check if app exists
    private boolean checkAppId(Long appId) {
        return appRepository.findByAppid(appId) == null;
    }

    // Check if rider exists
    private boolean checkRiderId(Long riderId) {
        return riderRepository.findByRiderId(riderId) == null;
    }

    // Check if client exists
    private boolean checkConsumerId(Long consumerId) {
        return consumerRepository.findByConsumerId(consumerId) == null;
    }

    // Check and return product if exists, null otherwise
    public Product checkAndGetProduct(Long id) {

        Optional<Product> value = productRepository.findById(id);

        if(value.isPresent())
            return value.get();

        return null;
    }

    // Check and return order if exists, null otherwise
    public Order checkAndGetOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty())
            return null;
        return order.get();
    }

    public Rider matchRider(Double latitude,Double longitude){
        List<Rider> riders = riderRepository.findAll();

        if(riders.size()==0)
            return null;

        Rider r1 = null;

        double distance = 0.0;


        for(Rider r:riders){
            if(distance == 0.0){
                distance = calculateDistance(latitude,longitude,r.getLatitude(),r.getLongitude());
                r1 = r;
            }
            else{
                System.out.println(r);
                if(calculateDistance(latitude,longitude,r.getLatitude(),r.getLongitude())<distance) {
                    distance = calculateDistance(latitude, longitude, r.getLatitude(), r.getLongitude());
                    r1 = r;
                }
            }
        }
        return r1;
    }

    public double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (earthRadius * c);
    }



}
