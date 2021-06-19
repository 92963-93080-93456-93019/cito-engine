package ua.tqs.cito.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
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
import ua.tqs.cito.repository.AppRepository;
import ua.tqs.cito.repository.ManagerRepository;
import ua.tqs.cito.repository.OrderRepository;
import ua.tqs.cito.utils.HttpResponses;
import ua.tqs.cito.utils.OrderStatusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AppServiceTest {
    @Mock
    private AppRepository appRepository;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private AppService appService;


    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void whenRegisterAppWithInvalidManager_ReturnInvalidManager() throws JsonProcessingException {
        Long managerId = 1L;

        given( managerRepository.findByManagerId(managerId)).willReturn(null);

        String request = "{\n" +
                "    \"tax\":50,\n" +
                "    \"name\": \"appfixe\",\n" +
                "    \"address\": \"Rua fixe\",\n" +
                "    \"schedule\": \"24/7\",\n" +
                "    \"image\":\"imagemfixe\"\n" +
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = appService.registerApp(managerId,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));
        assertThat(r.getBody(), is(HttpResponses.INVALID_MANAGER));
    }

    @Test
    public void whenRegisterAppWithInvalidTax_ReturnForbidden() throws JsonProcessingException {
        Long managerId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");

        Manager manager = new Manager(managerId,"Tiago","OLiveira","919191768","Rua do Corvo");
        manager.setApp(app);

        given( managerRepository.findByManagerId(managerId)).willReturn(manager);

        String request = "{\n" +
                "    \"tax\":\"\",\n" +
                "    \"name\": \"appfixe\",\n" +
                "    \"address\": \"Rua fixe\",\n" +
                "    \"schedule\": \"24/7\",\n" +
                "    \"image\":\"imagemfixe\"\n" +
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = appService.registerApp(managerId,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));
        assertThat(r.getBody(), is(HttpResponses.INVALID_TAX));
    }

    @Test
    public void whenRegisterAppWithInvalidName_ReturnForbidden() throws JsonProcessingException {
        Long managerId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");

        Manager manager = new Manager(managerId,"Tiago","OLiveira","919191768","Rua do Corvo");
        manager.setApp(app);

        given( managerRepository.findByManagerId(managerId)).willReturn(manager);

        String request = "{\n" +
                "    \"tax\":50,\n" +
                "    \"name\": \"\",\n" +
                "    \"address\": \"Rua fixe\",\n" +
                "    \"schedule\": \"24/7\",\n" +
                "    \"image\":\"imagemfixe\"\n" +
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = appService.registerApp(managerId,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));
        assertThat(r.getBody(), is(HttpResponses.INVALID_NAME));
    }

    @Test
    public void whenRegisterAppWithInvalidAddress_ReturnForbidden() throws JsonProcessingException {
        Long managerId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");

        Manager manager = new Manager(managerId,"Tiago","OLiveira","919191768","Rua do Corvo");
        manager.setApp(app);

        given( managerRepository.findByManagerId(managerId)).willReturn(manager);

        String request = "{\n" +
                "    \"tax\":50,\n" +
                "    \"name\": \"appfixe\",\n" +
                "    \"address\": \"\",\n" +
                "    \"schedule\": \"24/7\",\n" +
                "    \"image\":\"imagemfixe\"\n" +
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = appService.registerApp(managerId,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));
        assertThat(r.getBody(), is(HttpResponses.INVALID_APP_ADDRESS));
    }

    @Test
    public void whenRegisterAppWithInvalidImage_ReturnForbidden() throws JsonProcessingException {
        Long managerId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");

        Manager manager = new Manager(managerId,"Tiago","OLiveira","919191768","Rua do Corvo");
        manager.setApp(app);

        given( managerRepository.findByManagerId(managerId)).willReturn(manager);

        String request = "{\n" +
                "    \"tax\":50,\n" +
                "    \"name\": \"appfixe\",\n" +
                "    \"address\": \"Rua fixe\",\n" +
                "    \"schedule\": \"24/7\",\n" +
                "    \"image\":\"\"\n" +
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = appService.registerApp(managerId,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));
        assertThat(r.getBody(), is(HttpResponses.INVALID_IMAGE));
    }

    @Test
    public void whenRegisterAppWithInvalidSchedule_ReturnForbidden() throws JsonProcessingException {
        Long managerId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");

        Manager manager = new Manager(managerId,"Tiago","OLiveira","919191768","Rua do Corvo");
        manager.setApp(app);

        given( managerRepository.findByManagerId(managerId)).willReturn(manager);

        String request = "{\n" +
                "    \"tax\":50,\n" +
                "    \"name\": \"appfixe\",\n" +
                "    \"address\": \"Rua fixe\",\n" +
                "    \"schedule\": \"\",\n" +
                "    \"image\":\"imagemfixe\"\n" +
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = appService.registerApp(managerId,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));
        assertThat(r.getBody(), is(HttpResponses.INVALID_SCHEDULE));
    }

    @Test
    public void whenRegisterApp_ReturnCreated() throws JsonProcessingException {
        Long managerId = 1L;

        App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");

        Manager manager = new Manager(managerId,"Tiago","OLiveira","919191768","Rua do Corvo");
        manager.setApp(app);

        given( managerRepository.findByManagerId(managerId)).willReturn(manager);
        given( appRepository.save(app)).willReturn(app);

        String request = "{\n" +
                "    \"tax\":50,\n" +
                "    \"name\": \"appfixe\",\n" +
                "    \"address\": \"Rua fixe\",\n" +
                "    \"schedule\": \"24/7\",\n" +
                "    \"image\":\"imagemfixe\"\n" +
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = appService.registerApp(managerId,payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.CREATED)));
        assertThat(r.getBody(), is(HttpResponses.APP_CREATED));
    }

    @Test
    public void WhenGettingRevenueWithInvalidAppReturnForbidden(){
        Long appid = 1L;

        given(appRepository.findByAppid(appid)).willReturn(null);

        ResponseEntity<Object> r = appService.getRevenue(appid);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));
        assertThat(r.getBody(), is(HttpResponses.INVALID_APP));

    }

    @Test
    public void WhenGettingRevenueReturnOk(){
        Long appid = 1L;

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

        Order o1 = new Order(l,c1, OrderStatusEnum.PENDING,app,"Rua do corvo",50.0,50.0);

        List<Order> orders = new ArrayList<>();
        orders.add(o1);

        given(appRepository.findByAppid(appid)).willReturn(app);
        given(orderRepository.findOrdersByApp(app)).willReturn(orders);

        ResponseEntity<Object> r = appService.getRevenue(appid);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.OK)));
        AssertionsForClassTypes.assertThat(Double.parseDouble(r.getBody().toString()) ).isGreaterThan(0);

    }
}
