
package ua.tqs.cito.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import ua.tqs.cito.model.*;
import ua.tqs.cito.repository.*;
import ua.tqs.cito.utils.HttpResponses;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CitoApplication.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserRegisterServiceTest {

    @Mock
    private RiderRepository riderRepository;

    @InjectMocks
    private UserRegisterService userRegisterService;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void whenRegisterRiderWithNovehicle_ReturnForbidden() throws JsonProcessingException {

        String request= "{\n" +
                "    \"vehicle\":{\n" +
                "        \"name\": \"\",\n" +
                "        \"license\": \"00-00-00\"\n" +
                "    },\n" +
                "    \"rider\":{\n" +
                "        \"fname\": \"Dinis\",\n" +
                "        \"lname\": \"Cruz\",\n" +
                "        \"fnumber\": \"919191781\"\n" +
                "    }\n" +
                "\n" +
                "}";

        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = userRegisterService.registerRider(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_VEHICLE));
    }

    @Test
    public void whenRegisterRiderWithNoLicense_ReturnForbidden() throws JsonProcessingException {

        String request= "{\n" +
                "    \"vehicle\":{\n" +
                "        \"name\": \"Mercedes\",\n" +
                "        \"license\": \"\"\n" +
                "    },\n" +
                "    \"rider\":{\n" +
                "        \"fname\": \"Dinis\",\n" +
                "        \"lname\": \"Cruz\",\n" +
                "        \"fnumber\": \"919191781\"\n" +
                "    }\n" +
                "\n" +
                "}";

        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = userRegisterService.registerRider(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_VEHICLE));
    }

    @Test
    public void whenRegisterRiderWithNoFname_ReturnForbidden() throws JsonProcessingException {

        String request= "{\n" +
                "    \"vehicle\":{\n" +
                "        \"name\": \"Mercedes\",\n" +
                "        \"license\": \"00-00-00\"\n" +
                "    },\n" +
                "    \"rider\":{\n" +
                "        \"fname\": \"\",\n" +
                "        \"lname\": \"Cruz\",\n" +
                "        \"fnumber\": \"919191781\"\n" +
                "    }\n" +
                "\n" +
                "}";

        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = userRegisterService.registerRider(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_RIDER));
    }

    @Test
    public void whenRegisterRiderWithNoLname_ReturnForbidden() throws JsonProcessingException {

        String request= "{\n" +
                "    \"vehicle\":{\n" +
                "        \"name\": \"Mercedes\",\n" +
                "        \"license\": \"00-00-00\"\n" +
                "    },\n" +
                "    \"rider\":{\n" +
                "        \"fname\": \"Dinis\",\n" +
                "        \"lname\": \"\",\n" +
                "        \"fnumber\": \"919191781\"\n" +
                "    }\n" +
                "\n" +
                "}";

        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = userRegisterService.registerRider(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_RIDER));
    }

    @Test
    public void whenRegisterRiderWithNoFnumber_ReturnForbidden() throws JsonProcessingException {

        String request= "{\n" +
                "    \"vehicle\":{\n" +
                "        \"name\": \"Mercedes\",\n" +
                "        \"license\": \"00-00-00\"\n" +
                "    },\n" +
                "    \"rider\":{\n" +
                "        \"fname\": \"Dinis\",\n" +
                "        \"lname\": \"Cruz\",\n" +
                "        \"fnumber\": \"\"\n" +
                "    }\n" +
                "\n" +
                "}";

        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = userRegisterService.registerRider(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_RIDER));
    }

    @Test
    public void whenRegisterRider_ReturnCreated() throws JsonProcessingException {

        String request= "{\n" +
                "    \"vehicle\":{\n" +
                "        \"name\": \"Mercedes\",\n" +
                "        \"license\": \"00-00-00\"\n" +
                "    },\n" +
                "    \"rider\":{\n" +
                "        \"fname\": \"Dinis\",\n" +
                "        \"lname\": \"Cruz\",\n" +
                "        \"fnumber\": \"919191781\"\n" +
                "    }\n" +
                "\n" +
                "}";

        JsonNode payload = objectMapper.readTree(request);

        Rider r1 = new Rider("Dinis", "Cruz", "919191781","Mercedes","00-00-00");

        given(riderRepository.save(r1)).willReturn(r1);

        ResponseEntity<Object> r = userRegisterService.registerRider(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.CREATED)));

        assertThat(r.getBody(), is(HttpResponses.RIDER_SAVED));
    }

}