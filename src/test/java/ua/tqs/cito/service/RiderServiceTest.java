package ua.tqs.cito.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
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
import ua.tqs.cito.model.Rider;
import ua.tqs.cito.repository.*;
import ua.tqs.cito.utils.HttpResponses;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RiderServiceTest {

    @Mock
    private RiderRepository riderRepository;

    @InjectMocks
    private RiderService riderService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void whenRiderUpdatesAvailabilityWithInvalidId_ReturnForbidden() throws JsonProcessingException {
        long riderId = 1L;

        given(riderRepository.findByRiderId(riderId)).willReturn(null);

        String request = "{\n" +
                "        \"availability\": \"True\" "+
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = riderService.updateAvailability(payload,riderId);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_RIDER));
    }

    @Test
    public void whenRiderUpdatesAvailability_ReturnOK() throws JsonProcessingException {
        long riderId = 1L;

        Rider r1 = new Rider("Dinis", "Cruz", "919191781","Mercedes","00-00-00");


        given(riderRepository.findByRiderId(riderId)).willReturn(r1);

        String request = "{\n" +
                "        \"availability\": \"True\" "+
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = riderService.updateAvailability(payload,riderId);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.OK)));

        assertThat(r.getBody(), is(HttpResponses.RIDER_AVAILABILITY_UPDATED));
    }

    @Test
    public void whenRiderUpdatesLocationWithInvalidRider_ReturnForbidden() throws JsonProcessingException {
        long riderId = 1L;

        given(riderRepository.findByRiderId(riderId)).willReturn(null);

        String request = "{\n" +
                "        \"latitude\": 50.0 ,"+
                "        \"longitude\": 50.0 "+
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = riderService.updateLocation(payload,riderId);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_RIDER));
    }

    @Test
    public void whenRiderUpdatesLocationWithNoLatitude_ReturnForbidden() throws JsonProcessingException {
        long riderId = 1L;

        Rider r1 = new Rider("Dinis", "Cruz", "919191781","Mercedes","00-00-00");


        given(riderRepository.findByRiderId(riderId)).willReturn(r1);

        String request = "{\n" +
                "        \"latitude\": \"\" ,"+
                "        \"longitude\": 50.0 "+
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = riderService.updateLocation(payload,riderId);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.RIDER_LOCATION_INVALID));
    }

    @Test
    public void whenRiderUpdatesLocationWithToMuchLatitude_ReturnForbidden() throws JsonProcessingException {
        long riderId = 1L;

        Rider r1 = new Rider("Dinis", "Cruz", "919191781","Mercedes","00-00-00");


        given(riderRepository.findByRiderId(riderId)).willReturn(r1);

        String request = "{\n" +
                "        \"latitude\": 91.0 ,"+
                "        \"longitude\": 50.0 "+
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = riderService.updateLocation(payload,riderId);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.RIDER_LOCATION_INVALID));
    }

    @Test
    public void whenRiderUpdatesLocationWithToLessLatitude_ReturnForbidden() throws JsonProcessingException {
        long riderId = 1L;

        Rider r1 = new Rider("Dinis", "Cruz", "919191781","Mercedes","00-00-00");


        given(riderRepository.findByRiderId(riderId)).willReturn(r1);

        String request = "{\n" +
                "        \"latitude\": -91.0 ,"+
                "        \"longitude\": 50.0 "+
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = riderService.updateLocation(payload,riderId);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.RIDER_LOCATION_INVALID));
    }

    @Test
    public void whenRiderUpdatesLocationWithNoLongitude_ReturnForbidden() throws JsonProcessingException {
        long riderId = 1L;

        Rider r1 = new Rider("Dinis", "Cruz", "919191781","Mercedes","00-00-00");


        given(riderRepository.findByRiderId(riderId)).willReturn(r1);

        String request = "{\n" +
                "        \"latitude\": 50.0 ,"+
                "        \"longitude\": \"\" "+
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = riderService.updateLocation(payload,riderId);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.RIDER_LOCATION_INVALID));
    }

    @Test
    public void whenRiderUpdatesLocationWithToMuchLongitude_ReturnForbidden() throws JsonProcessingException {
        long riderId = 1L;

        Rider r1 = new Rider("Dinis", "Cruz", "919191781","Mercedes","00-00-00");


        given(riderRepository.findByRiderId(riderId)).willReturn(r1);

        String request = "{\n" +
                "        \"latitude\": 50.0 ,"+
                "        \"longitude\": 181.0 "+
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = riderService.updateLocation(payload,riderId);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.RIDER_LOCATION_INVALID));
    }

    @Test
    public void whenRiderUpdatesLocationWithToLessLongitude_ReturnForbidden() throws JsonProcessingException {
        long riderId = 1L;

        Rider r1 = new Rider("Dinis", "Cruz", "919191781","Mercedes","00-00-00");


        given(riderRepository.findByRiderId(riderId)).willReturn(r1);

        String request = "{\n" +
                "        \"latitude\": 50.0 ,"+
                "        \"longitude\": -181.0 "+
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = riderService.updateLocation(payload,riderId);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.RIDER_LOCATION_INVALID));
    }

    @Test
    public void whenRiderUpdatesLocation_ReturnOk() throws JsonProcessingException {
        long riderId = 1L;

        Rider r1 = new Rider("Dinis", "Cruz", "919191781","Mercedes","00-00-00");


        given(riderRepository.findByRiderId(riderId)).willReturn(r1);

        String request = "{\n" +
                "        \"latitude\": 50.0 ,"+
                "        \"longitude\": -50.0 "+
                "}";
        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = riderService.updateLocation(payload,riderId);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.OK)));

        assertThat(r.getBody(), is(HttpResponses.RIDER_LOCATION_UPDATED));
    }
}
