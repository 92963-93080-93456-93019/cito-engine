package ua.tqs.cito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.tqs.cito.model.App;
import ua.tqs.cito.model.Consumer;
import ua.tqs.cito.model.Manager;
import ua.tqs.cito.model.Product;
import ua.tqs.cito.repository.AppRepository;
import ua.tqs.cito.repository.ConsumerRepository;
import ua.tqs.cito.repository.ManagerRepository;
import ua.tqs.cito.repository.ProductRepository;
import ua.tqs.cito.utils.HttpResponses;


@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private AppRepository appRepository;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private ConsumerRepository consumerRepository;
	
	public ResponseEntity<Object> registerProduct(Long managerId, Long appid, Product p) {

		var app = appRepository.findByAppid(appid);
		if (app == null)
			return new ResponseEntity<>(HttpResponses.INVALID_APP, HttpStatus.NOT_FOUND);
		
		Manager manager = managerRepository.findManagerByApp(app);
		if (manager == null || !manager.getManagerId().equals(managerId))
			return new ResponseEntity<>(HttpResponses.MANAGER_NOT_FOUND_FOR_APP, HttpStatus.NOT_FOUND);
		
		if (p.getName() == null || p.getApp() == null || p.getDescription() == null || p.getImage() == null || p.getPrice() == null || p.getCategory() == null) {
			return new ResponseEntity<>(HttpResponses.PRODUCT_NOT_SAVED, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		productRepository.save(p);
		return new ResponseEntity<>(HttpResponses.PRODUCT_SAVED, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> getAllProducts(Long managerId, Long appid){

		var app = appRepository.findByAppid(appid);
		if (app == null)
			return new ResponseEntity<>(HttpResponses.INVALID_APP, HttpStatus.NOT_FOUND);

		Manager manager = managerRepository.findManagerByApp(app);
		if (manager == null || !manager.getManagerId().equals(managerId))
			return new ResponseEntity<>(HttpResponses.MANAGER_NOT_FOUND_FOR_APP, HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(productRepository.findByApp(app), HttpStatus.OK);
	}

	public ResponseEntity<Object> getAllProductsForClient(Long consumerId, Long appid){

		var app = appRepository.findByAppid(appid);
		if (app == null)
			return new ResponseEntity<>(HttpResponses.INVALID_APP, HttpStatus.NOT_FOUND);

		if (!checkConsumerId(consumerId))
			return new ResponseEntity<>(HttpResponses.INVALID_CONSUMER, HttpStatus.FORBIDDEN);

		return new ResponseEntity<>(productRepository.findByApp(app), HttpStatus.OK);
	}

	// Check if client exists
	private boolean checkConsumerId(Long consumerId) {
		return consumerRepository.findByConsumerId(consumerId) != null;
	}
}

