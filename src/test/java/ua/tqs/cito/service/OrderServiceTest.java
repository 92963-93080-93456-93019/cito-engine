
package ua.tqs.cito.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ua.tqs.cito.CitoApplication;
import ua.tqs.cito.model.*;
import ua.tqs.cito.repository.*;
import ua.tqs.cito.utils.HttpResponses;
import ua.tqs.cito.utils.OrderStatusEnum;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CitoApplication.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AppRepository appRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ConsumerRepository consumerRepository;

    @Mock
    private RiderRepository riderRepository;

    @InjectMocks
    private OrderService orderService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void whenGetOrdersReturnList() {
        Long clientId = 1L;
        Long appid = 1L;
        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);

        ProductListItem pli1 = new ProductListItem(new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image"),2);
        ProductListItem pli2 = new ProductListItem(new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image"),3);

        ProductListItem pli3 = new ProductListItem(new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image"),1);
        ProductListItem pli4 = new ProductListItem(new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image"),2);


        List<ProductListItem> l = new ArrayList<>();
        l.add(pli1);
        l.add(pli2);

        List<ProductListItem> l2 = new ArrayList<>();
        l2.add(pli3);
        l2.add(pli4);

        List<Order> orders = new ArrayList<>();

        Order o1 = new Order(1L, l,c1, OrderStatusEnum.PENDING,app,"Fatima",50.0,50.0);
        Order o2 = new Order(2L, l2,c1, OrderStatusEnum.PENDING,app,"Fatima",50.0,50.0);

        orders.add(o1);
        orders.add(o2);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( orderRepository.findOrdersByEndConsumer(c1)).willReturn(orders);

        ResponseEntity<Object> r = orderService.getOrdersForConsumer(clientId,appid);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.OK)));
        assertThat(r.getBody(), is(orders));
    }

    @Test
    public void whenGetOrdersWithInvalidApp_ReturnForbidden() {
        Long clientId = 1L;
        Long appid = 1L;
        given( appRepository.findByAppid(appid)).willReturn(null);

        ResponseEntity<Object> r = orderService.getOrdersForConsumer(clientId,appid);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_APP));
    }

    @Test
    public void whenGetOrdersWithInvalidConsumer_ReturnForbidden() {
        Long clientId = 1L;
        Long appid = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");


        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(null);

        ResponseEntity<Object> r = orderService.getOrdersForConsumer(clientId,appid);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_CONSUMER));
    }

    @Test
    public void whenRegisterOrdersWithInvalidApp_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        given( appRepository.findByAppid(appid)).willReturn(null);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": 50.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_APP));
    }

    @Test
    public void whenRegisterOrdersWithInvalidConsumer_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");


        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(null);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": 50.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_CONSUMER));
    }

    @Test
    public void whenRegisterOrdersWithNotEnoughProducts_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);

        String request = "{\"products\":[],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": 50.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INSUFFICIENT_PRODUCTS));
    }

    @Test
    public void whenRegisterOrdersWithInvalidProducts_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);
        Product p1 = new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");
        Product p2 = new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");

        Optional<Product> opt1 = Optional.of(p1);
        Optional<Product> opt2 = Optional.empty();

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( productRepository.findById(3L)).willReturn(opt1);
        given( productRepository.findById(4L)).willReturn(opt2);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": 50.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_PRODUCT.replace("#", "4")));
    }

    @Test
    public void whenRegisterOrdersWithInvalidAddress_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);
        Product p1 = new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");
        Product p2 = new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");

        Optional<Product> opt1 = Optional.of(p1);
        Optional<Product> opt2 = Optional.of(p2);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( productRepository.findById(3L)).willReturn(opt1);
        given( productRepository.findById(4L)).willReturn(opt2);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": 50.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_ADDRESS));
    }

    @Test
    public void whenRegisterOrdersWithNoLatitude_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);
        Product p1 = new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");
        Product p2 = new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");

        Optional<Product> opt1 = Optional.of(p1);
        Optional<Product> opt2 = Optional.of(p2);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( productRepository.findById(3L)).willReturn(opt1);
        given( productRepository.findById(4L)).willReturn(opt2);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Fatima\",\"deliverInPerson\":true,\"latitude\": \"\",\"longitude\": 50.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.ORDER_LOCATION_INVALID));
    }

    @Test
    public void whenRegisterOrdersWithToMuchLatitude_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);
        Product p1 = new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");
        Product p2 = new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");

        Optional<Product> opt1 = Optional.of(p1);
        Optional<Product> opt2 = Optional.of(p2);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( productRepository.findById(3L)).willReturn(opt1);
        given( productRepository.findById(4L)).willReturn(opt2);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Fatima\",\"deliverInPerson\":true,\"latitude\": 91.0,\"longitude\": 50.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.ORDER_LOCATION_INVALID));
    }

    @Test
    public void whenRegisterOrdersWithToLessLatitude_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);
        Product p1 = new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");
        Product p2 = new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");

        Optional<Product> opt1 = Optional.of(p1);
        Optional<Product> opt2 = Optional.of(p2);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( productRepository.findById(3L)).willReturn(opt1);
        given( productRepository.findById(4L)).willReturn(opt2);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Fatima\",\"deliverInPerson\":true,\"latitude\": -91.0,\"longitude\": 50.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.ORDER_LOCATION_INVALID));
    }

    @Test
    public void whenRegisterOrdersWithNoLongitude_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);
        Product p1 = new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");
        Product p2 = new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");

        Optional<Product> opt1 = Optional.of(p1);
        Optional<Product> opt2 = Optional.of(p2);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( productRepository.findById(3L)).willReturn(opt1);
        given( productRepository.findById(4L)).willReturn(opt2);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Fatima\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": \"\"}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.ORDER_LOCATION_INVALID));
    }

    @Test
    public void whenRegisterOrdersWithToMuchLongitude_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);
        Product p1 = new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");
        Product p2 = new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");

        Optional<Product> opt1 = Optional.of(p1);
        Optional<Product> opt2 = Optional.of(p2);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( productRepository.findById(3L)).willReturn(opt1);
        given( productRepository.findById(4L)).willReturn(opt2);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Fatima\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": 181.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.ORDER_LOCATION_INVALID));
    }

    @Test
    public void whenRegisterOrdersWithToLessLongitude_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);
        Product p1 = new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");
        Product p2 = new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");

        Optional<Product> opt1 = Optional.of(p1);
        Optional<Product> opt2 = Optional.of(p2);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( productRepository.findById(3L)).willReturn(opt1);
        given( productRepository.findById(4L)).willReturn(opt2);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Fatima\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": -181.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.ORDER_LOCATION_INVALID));
    }

    @Test
    public void whenRegisterOrdersButNoRidersAvailable_ReturnNotFound() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);
        Product p1 = new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");
        Product p2 = new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");

        Optional<Product> opt1 = Optional.of(p1);
        Optional<Product> opt2 = Optional.of(p2);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( productRepository.findById(3L)).willReturn(opt1);
        given( productRepository.findById(4L)).willReturn(opt2);
        given( riderRepository.findAll()).willReturn(new ArrayList<>());

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Fatima\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": 50.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.NOT_FOUND)));

        assertThat(r.getBody(), is(HttpResponses.NO_RIDERS_AVAILABLE));
    }

    @Test
    public void whenRegisterOrders_ReturnOK() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);
        Product p1 = new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");
        Product p2 = new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image");

        Optional<Product> opt1 = Optional.of(p1);
        Optional<Product> opt2 = Optional.of(p2);

        ProductListItem pli1 = new ProductListItem(p1,2);
        ProductListItem pli2 = new ProductListItem(p2,3);

        List<ProductListItem> l = new ArrayList<>();
        l.add(pli1);
        l.add(pli2);

        List<Rider> riders = new ArrayList<>();

        Rider r1 = new Rider(1L,"Dinis","Cruz","912223334","Mercedes","00-00-00");
        Rider r2 = new Rider(2L,"Tiago","Oliveira","912223333","Ford","11-11-11");
        r1.setLongitude(50.0);
        r1.setLatitude(50.0);
        r2.setLongitude(80.0);
        r2.setLongitude(-60.0);
        riders.add(r1);
        riders.add(r2);

        Order o1 = new Order(l,c1, OrderStatusEnum.PENDING,app,"Rua do corvo",50.0,50.0);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( productRepository.findById(3L)).willReturn(opt1);
        given( productRepository.findById(4L)).willReturn(opt2);
        given( riderRepository.findAll()).willReturn(riders);
        given( orderRepository.save(o1)).willReturn(o1);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Fatima\",\"deliverInPerson\":true,\"latitude\": 50.0,\"longitude\": 50.0}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.CREATED)));

        assertThat(r.getBody(), is(HttpResponses.ORDER_SAVED));
    }

    @Test
    public void whenUpdateOrders_ReturnOK() throws JsonProcessingException {
        Long riderId = 1L;
        Long appid = 1L;
        Long orderId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Rider r1 = new Rider(1L,"Dinis","Cruz","912223334","Mercedes","00-00-00");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);

        ProductListItem pli1 = new ProductListItem(new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image"),2);
        ProductListItem pli2 = new ProductListItem(new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image"),3);

        List<ProductListItem> l = new ArrayList<>();
        l.add(pli1);
        l.add(pli2);

        Order o1 = new Order(1L, l,c1, OrderStatusEnum.PENDING,app,"Fatima",50.0,50.0);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( riderRepository.findByRiderId(r1.getRiderId())).willReturn(r1);
        given( orderRepository.findById(orderId)).willReturn(Optional.of(o1));

        String request = "{\"orderId\":1,\"status\":\"GOING_TO_BUY\"}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.updateOrder(riderId,payload);
        System.out.println(r.toString());
        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.OK)));

        assertThat(r.getBody(), is(HttpResponses.ORDER_UPDATED.replace("#", "GOING_TO_BUY")));
    }

    @Test
    public void whenUpdateOrdersWithInvalidRider_ReturnForbidden() throws JsonProcessingException {
        Long riderId = 1L;
        Long appid = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( riderRepository.findByRiderId(riderId)).willReturn(null);

        String request = "{\"orderId\":1,\"status\":\"GOING_TO_BUY\"}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.updateOrder(riderId,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_RIDER));
    }

    @Test
    public void whenUpdateOrdersWithInvalidSTATUS_ReturnForbidden() throws JsonProcessingException {
        Long riderId = 1L;
        Long appid = 1L;
        Long orderId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Rider r1 = new Rider(1L,"Dinis","Cruz","912223334","Mercedes","00-00-00");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);

        ProductListItem pli1 = new ProductListItem(new Product(3L, "Benuron","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image"),2);
        ProductListItem pli2 = new ProductListItem(new Product(4L, "Brufen","Farmacia Geral","Great for Small pains!",app,15.0,"someBase64Image"),3);

        List<ProductListItem> l = new ArrayList<>();
        l.add(pli1);
        l.add(pli2);

        Order o1 = new Order(1L, l,c1, OrderStatusEnum.PENDING,app,"Fatima",50.0,50.0);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( riderRepository.findByRiderId(r1.getRiderId())).willReturn(r1);
        given( orderRepository.findById(orderId)).willReturn(Optional.of(o1));

        String request = "{\"orderId\":1,\"status\":\"INVALID_STATUS\"}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.updateOrder(riderId,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_STATUS));
    }

        @Test
    public void whenRatingRiderWithInvalidApp_ReturnInvalidApp(){
        Long appid = 1L;
        Long clientId = 1L;
        Long riderId=16L;
        int rating = 5;

        given( appRepository.findByAppid(appid)).willReturn(null);

        ResponseEntity<Object> r = orderService.consumerRatesRider(clientId,riderId,rating,appid);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_APP));
    }

    @Test
    public void whenRatingRiderWithInvalidConsumer_ReturnInvalidConsumer(){
        Long appid = 1L;
        Long clientId = 1L;
        Long riderId=16L;
        int rating = 5;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(null);

        ResponseEntity<Object> r = orderService.consumerRatesRider(clientId,riderId,rating,appid);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_CONSUMER));
    }

    @Test
    public void whenRatingInvalidRider_ReturnInvalidRider(){
        Long appid = 1L;
        Long clientId = 1L;
        Long riderId=16L;
        int rating = 5;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( riderRepository.findByRiderId(riderId)).willReturn(null);

        ResponseEntity<Object> r = orderService.consumerRatesRider(clientId,riderId,rating,appid);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_RIDER));
    }


    @Test
    public void whenRatingRider_ReturnOk(){
        Long appid = 1L;
        Long clientId = 1L;
        Long riderId=16L;
        int rating = 5;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
        Rider r1 = new Rider(1L,"Dinis","Cruz","912223334","Mercedes","00-00-00");
        Consumer c1 = new Consumer(1L,"Duarte","Mortagua","919191919","Fatima",app);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( riderRepository.findByRiderId(riderId)).willReturn(r1);

        ResponseEntity<Object> r = orderService.consumerRatesRider(clientId,riderId,rating,appid);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.OK)));

        assertThat(r.getBody(), is(HttpResponses.RIDER_RATED));
    }

}
