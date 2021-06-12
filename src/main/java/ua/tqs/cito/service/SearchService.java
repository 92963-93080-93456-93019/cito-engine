package ua.tqs.cito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.tqs.cito.model.App;
import ua.tqs.cito.repository.AppRepository;
import ua.tqs.cito.model.Product;
import ua.tqs.cito.repository.ConsumerRepository;
import ua.tqs.cito.repository.ProductRepository;
import ua.tqs.cito.utils.HttpResponses;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private ConsumerRepository consumerRepository;

    // Client gets products when searching by name
    public ResponseEntity<Object> getProductsByQuery(Long consumerId, Long appid, String query) {

        App app = appRepository.findByAppid(appid);

        if (app == null)
            return new ResponseEntity<>(HttpResponses.INVALID_APP, HttpStatus.FORBIDDEN);

        if (!checkConsumerId(consumerId))
            return new ResponseEntity<>(HttpResponses.INVALID_CONSUMER, HttpStatus.FORBIDDEN);

        List<Product> products = productRepository.findByNameLikeAndApp("%" + query + "%",app);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Check if app exists
    private boolean checkAppId(Long appId) {
        return appRepository.findByAppid(appId) != null;
    }

    // Check if client exists
    private boolean checkConsumerId(Long consumerId) {
        return consumerRepository.findByConsumerId(consumerId) != null;
    }

}


