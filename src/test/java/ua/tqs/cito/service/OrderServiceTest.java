
package ua.tqs.cito.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ua.tqs.cito.CitoApplication;
import ua.tqs.cito.model.*;
import ua.tqs.cito.repository.*;
import ua.tqs.cito.service.OrderService;
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

    /*@Test
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

        Order o1 = new Order(1L, l,c1, OrderStatusEnum.PENDING,app,"Fatima");
        Order o2 = new Order(2L, l2,c1, OrderStatusEnum.PENDING,app,"Fatima");

        orders.add(o1);
        orders.add(o2);

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( orderRepository.findOrdersByEndConsumer(c1)).willReturn(orders);

        ResponseEntity<Object> r = orderService.getOrders(clientId,appid);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.OK)));
        assertThat(r.getBody(), is(orders));
    }

    @Test
    public void whenGetOrdersWithInvalidApp_ReturnForbidden() {
        Long clientId = 1L;
        Long appid = 1L;
        given( appRepository.findByAppid(appid)).willReturn(null);

        ResponseEntity<Object> r = orderService.getOrders(clientId,appid);

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

        ResponseEntity<Object> r = orderService.getOrders(clientId,appid);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_CONSUMER));
    }

    @Test
    public void whenRegisterOrdersWithInvalidApp_ReturnForbidden() throws JsonProcessingException {
        Long appid = 1L;
        Long clientId = 1L;

        given( appRepository.findByAppid(appid)).willReturn(null);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true}}";
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

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true}}";
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

        String request = "{\"products\":[],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true}}";
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

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true}}";
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

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"\",\"deliverInPerson\":true}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_ADDRESS));
    }

    @Test
    public void whenRegisterOrders_ReturnOk() throws JsonProcessingException {
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

        Order o1 = new Order(l,c1, OrderStatusEnum.PENDING,app,"Rua do corvo");
        System.out.println("test: " + o1.toString());

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( consumerRepository.findByConsumerId(clientId)).willReturn(c1);
        given( productRepository.findById(3L)).willReturn(opt1);
        given( productRepository.findById(4L)).willReturn(opt2);
        given( orderRepository.save(o1)).willReturn(o1);

        String request = "{\"products\":[{\"id\":3,\"quantity\":2},{\"id\":4,\"quantity\":3}],\"info\":{\"appid\":1,\"userId\":1,\"deliveryAddress\":\"Rua do corvo\",\"deliverInPerson\":true}}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = orderService.registerOrder(clientId,appid,payload);

        System.out.println(orderRepository.save(o1));
        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.CREATED)));

        assertThat(r.getBody(), is(HttpResponses.ORDER_SAVED));
    }

    @Test
    public void whenUpdateOrders_ReturnOK() {
        Long RiderId = 1L;
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

        Order o1 = new Order(1L, l,c1, OrderStatusEnum.PENDING,app,"Fatima");

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( riderRepository.findByRiderId(r1.getRiderId())).willReturn(r1);
        given( orderRepository.getById(orderId)).willReturn(o1);

        ResponseEntity<Object> r = orderService.updateOrder(RiderId,appid,orderId,"GOING_TO_BUY");

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.OK)));

        assertThat(r.getBody(), is(HttpResponses.ORDER_UPDATED.replace("#", "GOING_TO_BUY")));
    }

    @Test
    public void whenUpdateOrdersWithInvalidRider_ReturnForbidden() {
        Long RiderId = 1L;
        Long appid = 1L;
        Long orderId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( riderRepository.findByRiderId(RiderId)).willReturn(null);

        ResponseEntity<Object> r = orderService.updateOrder(RiderId,appid,orderId,"GOING_TO_BUY");

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_RIDER));
    }

    @Test
    public void whenUpdateOrdersWithInvalidAPP_ReturnForbidden() {
        Long RiderId = 1L;
        Long appid = 1L;
        Long orderId = 1L;

        given( appRepository.findByAppid(appid)).willReturn(null);

        ResponseEntity<Object> r = orderService.updateOrder(RiderId,appid,orderId,"GOING_TO_BUY");

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_APP));
    }

    @Test
    public void whenUpdateOrdersWithInvalidSTATUS_ReturnForbidden() {
        Long RiderId = 1L;
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

        Order o1 = new Order(1L, l,c1, OrderStatusEnum.PENDING,app,"Fatima");

        given( appRepository.findByAppid(appid)).willReturn(app);
        given( riderRepository.findByRiderId(r1.getRiderId())).willReturn(r1);
        given( orderRepository.getById(orderId)).willReturn(o1);



        ResponseEntity<Object> r = orderService.updateOrder(RiderId,appid,orderId,"INVALID_STATUS");

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_STATUS));
    }*/

}
