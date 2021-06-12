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
        System.out.println(r1.toString());
        riderRepository.save(r1);
        return new ResponseEntity<>(HttpResponses.RIDER_SAVED, HttpStatus.CREATED);
    }

}
