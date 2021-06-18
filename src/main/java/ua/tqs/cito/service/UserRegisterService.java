package ua.tqs.cito.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.tqs.cito.model.*;
import ua.tqs.cito.repository.*;
import ua.tqs.cito.utils.HttpResponses;
import ua.tqs.cito.utils.OrderStatusEnum;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRegisterService {

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public ResponseEntity<Object> registerRider(JsonNode payload) {

        if (payload.path("vehicle").path("name").asText().equals("")) {
            return new ResponseEntity<>(HttpResponses.INVALID_VEHICLE, HttpStatus.FORBIDDEN);
        }

        if (payload.path("vehicle").path("license").asText().equals("")) {
            return new ResponseEntity<>(HttpResponses.INVALID_VEHICLE, HttpStatus.FORBIDDEN);
        }

        if (payload.path("rider").path("fname").asText().equals("")) {
            return new ResponseEntity<>(HttpResponses.INVALID_RIDER, HttpStatus.FORBIDDEN);
        }

        if (payload.path("rider").path("lname").asText().equals("")) {
            return new ResponseEntity<>(HttpResponses.INVALID_RIDER, HttpStatus.FORBIDDEN);
        }

        if (payload.path("rider").path("fnumber").asText().equals("")) {
            return new ResponseEntity<>(HttpResponses.INVALID_RIDER, HttpStatus.FORBIDDEN);
        }

        Rider r1 = new Rider(payload.path("rider").path("fname").asText(), payload.path("rider").path("lname").asText(), payload.path("rider").path("fnumber").asText(), payload.path("vehicle").path("name").asText(), payload.path("vehicle").path("license").asText());
        riderRepository.save(r1);
        return new ResponseEntity<>(HttpResponses.RIDER_SAVED, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> registerManager(JsonNode payload) {

        if (payload.path("fname").asText().equals("")) {
            return new ResponseEntity<>(HttpResponses.INVALID_MANAGER_FIRSTNAME, HttpStatus.FORBIDDEN);
        }

        if (payload.path("lname").asText().equals("")) {
            return new ResponseEntity<>(HttpResponses.INVALID_MANAGER_LASTNAME, HttpStatus.FORBIDDEN);
        }

        if (payload.path("phone").asText().equals("")) {
            return new ResponseEntity<>(HttpResponses.INVALID_MANAGER_PHONE, HttpStatus.FORBIDDEN);
        }

        if (payload.path("address").asText().equals("")) {
            return new ResponseEntity<>(HttpResponses.INVALID_MANAGER_ADDRESS, HttpStatus.FORBIDDEN);
        }

        Manager m1 = new Manager(1L,payload.path("fname").asText(), payload.path("fname").asText(),payload.path("phone").asText(), payload.path("address").asText());
        managerRepository.save(m1);
        return new ResponseEntity<>(HttpResponses.MANAGER_SAVED, HttpStatus.CREATED);
    }

    

}
