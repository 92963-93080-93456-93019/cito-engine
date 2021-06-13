package ua.tqs.cito.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ua.tqs.cito.CitoApplication;
import ua.tqs.cito.model.App;
import ua.tqs.cito.model.Consumer;
import ua.tqs.cito.model.Manager;
import ua.tqs.cito.model.Product;
import ua.tqs.cito.repository.AppRepository;
import ua.tqs.cito.repository.ConsumerRepository;
import ua.tqs.cito.repository.ManagerRepository;
import ua.tqs.cito.repository.ProductRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CitoApplication.class)
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@Mock
	private ProductRepository productRepository;

	@Mock
	private AppRepository appRepository;

	@Mock
	private ManagerRepository managerRepository;

	@Mock
	private ConsumerRepository consumerRepository;
	
	@InjectMocks
	private ProductService productService;
    
    @Test
    public void whenCreateValidproductCreateIt() {
    	Long managerId = 1L;
    	Long appid = 1L;
    	App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
    	Product p = new Product("Benuron","Farmácia Geral","Great for small pains!",app,13.00,"somebase64string");
    	Manager m = new Manager(managerId,"João","Alfredo","93943856","Rua Santo Jesus",app);
    	
    	given( appRepository.findByAppid(appid)).willReturn(app);
    	given( managerRepository.findManagerByApp(app)).willReturn(m);
        given( productRepository.save(p)).willReturn(p);
        
        ResponseEntity<Object> r = productService.registerProduct(managerId,appid, p);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.CREATED)));
        verify(productRepository, VerificationModeFactory.times(1)).save(p);
    }
    
    @Test
    public void whenCreateInvalidProductBecauseAppReturnInvalidApp() {
    	Long managerId = 1L;
    	Long appid = 1L;
    	App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
    	Product p = new Product("Benuron","Farmácia Geral","Great for small pains!",app,13.00,"somebase64string");
        
        ResponseEntity<Object> r = productService.registerProduct(managerId,appid, p);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.NOT_FOUND)));
        assertThat(r.getBody(), is("{\"code\" : 403, \"message\" : \"Invalid appId.\"}"));
    }
    
    @Test
    public void whenCreateInvalidProductBecauseManagerReturnManagerNotFound() {
    	Long managerId = 1L;
    	Long appid = 1L;
    	App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
    	Product p = new Product("Benuron","Farmácia Geral","Great for small pains!",app,13.00,"somebase64string");
        
    	given( appRepository.findByAppid(appid)).willReturn(app);
    	
        ResponseEntity<Object> r = productService.registerProduct(managerId,appid, p);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.NOT_FOUND)));
        assertThat(r.getBody(), is("{\"code\" : 404, \"message\" : \"Manager not found for app.\"}"));
    }
    
    @Test
    public void whenGetAllProductsReturnThem() {
    	Long managerId = 1L;
    	Long appid = 1L;
    	App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
    	Product p = new Product("Benuron","Farmácia Geral","Great for small pains!",app,13.00,"somebase64string");
    	Manager m = new Manager(managerId,"João","Alfredo","93943856","Rua Santo Jesus",app);
    	ArrayList<Product> ar = new ArrayList<>();
    	ar.add(p);
    	
    	given( appRepository.findByAppid(appid)).willReturn(app);
    	given( managerRepository.findManagerByApp(app)).willReturn(m);
        given( productRepository.findByApp(app)).willReturn(ar);
        
        ResponseEntity<Object> r_get = productService.getAllProducts(managerId,appid);

        assertThat(r_get.getStatusCode(), is(samePropertyValuesAs(HttpStatus.OK)));
        verify(productRepository, VerificationModeFactory.times(1)).findByApp(app);
    }
    
    @Test
    public void whenGetAllProductsWithInvalidAppReturnInvalidApp() {
    	Long managerId = 1L;
    	Long appid = 1L;
    	App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
    	Product p = new Product("Benuron","Farmácia Geral","Great for small pains!",app,13.00,"somebase64string");
    	Manager m = new Manager(managerId,"João","Alfredo","93943856","Rua Santo Jesus",app);

        
        ResponseEntity<Object> r_get = productService.getAllProducts(managerId,appid);

        assertThat(r_get.getStatusCode(), is(samePropertyValuesAs(HttpStatus.NOT_FOUND)));
        assertThat(r_get.getBody(), is("{\"code\" : 403, \"message\" : \"Invalid appId.\"}"));
    }
    
    @Test
    public void whengetAllProductBecauseManagerReturnManagerNotFound() {
    	Long managerId = 1L;
    	Long appid = 1L;
    	App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
    	Product p = new Product("Benuron","Farmácia Geral","Great for small pains!",app,13.00,"somebase64string");
        
    	given( appRepository.findByAppid(appid)).willReturn(app);
    	
        ResponseEntity<Object> r_get = productService.getAllProducts(managerId,appid);

        assertThat(r_get.getStatusCode(), is(samePropertyValuesAs(HttpStatus.NOT_FOUND)));
        assertThat(r_get.getBody(), is("{\"code\" : 404, \"message\" : \"Manager not found for app.\"}"));
    }
    
    @Test
    public void whenGetAllProductsForClientReturnThem() {
    	Long consumerId = 1L;
    	Long appid = 1L;
    	App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
    	Product p = new Product("Benuron","Farmácia Geral","Great for small pains!",app,13.00,"somebase64string");
    	Consumer c = new Consumer(1L,"Tiago","Oliveira","912318734","Rua do Corvo 455",app);
    	ArrayList<Product> ar = new ArrayList<>();
    	ar.add(p);
    	
    	given( appRepository.findByAppid(appid)).willReturn(app);
    	given( consumerRepository.findByConsumerId(consumerId)).willReturn(c);
    	given( productRepository.findByApp(app)).willReturn(ar);
        
        ResponseEntity<Object> r_get = productService.getAllProductsForClient(consumerId,appid);

        assertThat(r_get.getStatusCode(), is(samePropertyValuesAs(HttpStatus.OK)));
        verify(productRepository, VerificationModeFactory.times(1)).findByApp(app);
    }
    
    @Test
    public void whenGetAllProductsForClientWithInvalidAppReturnInvalidApp() {
    	Long consumerId = 1L;
    	Long appid = 1L;
    	App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
    	Product p = new Product("Benuron","Farmácia Geral","Great for small pains!",app,13.00,"somebase64string");
    	Consumer c = new Consumer(1L,"Tiago","Oliveira","912318734","Rua do Corvo 455",app);
        
        ResponseEntity<Object> r_get = productService.getAllProductsForClient(consumerId,appid);

        assertThat(r_get.getStatusCode(), is(samePropertyValuesAs(HttpStatus.NOT_FOUND)));
        assertThat(r_get.getBody(), is("{\"code\" : 403, \"message\" : \"Invalid appId.\"}"));
    }
    
    @Test
    public void whengetAllProductForClientBecauseConsumerReturnConsumerNotFound() {
    	Long consumerId = 1L;
    	Long appid = 1L;
    	App app = new App(1L,2.40, "Farmácia Armando", "Rua do Cabeço", "8-19h", "someBase&4Image");
    	Product p = new Product("Benuron","Farmácia Geral","Great for small pains!",app,13.00,"somebase64string");
        
    	given( appRepository.findByAppid(appid)).willReturn(app);
    	
        ResponseEntity<Object> r_get = productService.getAllProductsForClient(consumerId,appid);

        assertThat(r_get.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));
        assertThat(r_get.getBody(), is("{\"code\" : 403, \"message\" : \"Invalid clientId.\"}"));
    }
	
	
}