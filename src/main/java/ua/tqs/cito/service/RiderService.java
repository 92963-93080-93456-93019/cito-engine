package ua.tqs.cito.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.tqs.cito.model.Consumer;
import ua.tqs.cito.model.Order;
import ua.tqs.cito.model.Rider;
import ua.tqs.cito.repository.RiderRepository;
import ua.tqs.cito.utils.HttpResponses;

@Service
public class RiderService {
    @Autowired
    private RiderRepository riderRepository;

    public ResponseEntity<Object> updateAvailability(JsonNode payload, Long riderId) {
        if (checkRiderId(riderId))
            return new ResponseEntity<>(HttpResponses.INVALID_RIDER, HttpStatus.FORBIDDEN);

        Rider r1 = riderRepository.findByRiderId(riderId);

        if(payload.path("availability").asBoolean())
            return new ResponseEntity<>(HttpResponses.RIDER_AVAILABILITY_INVALID, HttpStatus.FORBIDDEN);

        Boolean availability = payload.path("availability").asBoolean();

        r1.setIfAvailable(availability);

        return new ResponseEntity<>(HttpResponses.RIDER_AVAILABILITY_UPDATED, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateLocation(JsonNode payload, Long riderId) {
        if (checkRiderId(riderId))
            return new ResponseEntity<>(HttpResponses.INVALID_RIDER, HttpStatus.FORBIDDEN);

        Rider r1 = riderRepository.findByRiderId(riderId);

        if(payload.path("latitude").asText().equals(""))
            return new ResponseEntity<>(HttpResponses.RIDER_LOCATION_INVALID, HttpStatus.FORBIDDEN);

        double latitude = Double.parseDouble(payload.path("latitude").asText());

        if(latitude>90 || latitude <-90)
            return new ResponseEntity<>(HttpResponses.RIDER_LOCATION_INVALID, HttpStatus.FORBIDDEN);

        if(payload.path("longitude").asText().equals(""))
            return new ResponseEntity<>(HttpResponses.RIDER_LOCATION_INVALID, HttpStatus.FORBIDDEN);

        double longitude = Double.parseDouble(payload.path("longitude").asText());

        if(longitude>180 || longitude <-180)
            return new ResponseEntity<>(HttpResponses.RIDER_LOCATION_INVALID, HttpStatus.FORBIDDEN);

        r1.setLatitude(latitude);
        r1.setLongitude(longitude);

        return new ResponseEntity<>(HttpResponses.RIDER_LOCATION_UPDATED, HttpStatus.OK);

    }

    // Check if rider exists
    private boolean checkRiderId(Long riderId) {
        return riderRepository.findByRiderId(riderId) == null;
    }


}
