package ua.tqs.cito.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.tqs.cito.model.*;
import ua.tqs.cito.repository.AppRepository;
import ua.tqs.cito.repository.ManagerRepository;
import ua.tqs.cito.utils.HttpResponses;
import ua.tqs.cito.utils.OrderStatusEnum;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private ManagerRepository managerRepository;

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

        App app = new App(tax,name,address,schedule,image);
        Manager manager = managerRepository.findByManagerId(managerId);
        manager.setApp(app);
        appRepository.save(app);
        return new ResponseEntity<>(HttpResponses.APP_CREATED, HttpStatus.CREATED);
    }

    // Check if manager exists
    private boolean checkManagerId(Long managerId) {
        return managerRepository.findByManagerId(managerId) != null;
    }
}
