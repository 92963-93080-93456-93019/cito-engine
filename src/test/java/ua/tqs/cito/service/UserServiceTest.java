
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

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock
    private RiderRepository riderRepository;

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private UserService userService;

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

        ResponseEntity<Object> r = userService.registerRider(payload);

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

        ResponseEntity<Object> r = userService.registerRider(payload);

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

        ResponseEntity<Object> r = userService.registerRider(payload);

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

        ResponseEntity<Object> r = userService.registerRider(payload);

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

        ResponseEntity<Object> r = userService.registerRider(payload);

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

        ResponseEntity<Object> r = userService.registerRider(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.CREATED)));

        assertThat(r.getBody(), is(HttpResponses.RIDER_SAVED));
    }

    @Test
    public void whenRegisterManagerWithNoFnameReturnForbidden() throws JsonProcessingException {

        String request= "{\n" +
                "    \"fname\":\"\",\n" +
                "\n\"lname\": \"Oliveira\",\n" +
                "\n\"address\": \"Rua fixe\",\n" +
                "\n\"phone\": \"919191785\"\n" +
                "}";

        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = userService.registerManager(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_MANAGER_FIRSTNAME));
    }

    @Test
    public void whenRegisterManagerWithNoLnameReturnForbidden() throws JsonProcessingException {

        String request= "{\n" +
                "    \"fname\":\"Tiago\",\n" +
                "\n\"lname\": \"\",\n" +
                "\n\"address\": \"Rua fixe\",\n" +
                "\n\"phone\": \"919191785\"\n" +
                "}";

        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = userService.registerManager(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_MANAGER_LASTNAME));
    }


    @Test
    public void whenRegisterManagerWithNoAddressReturnForbidden() throws JsonProcessingException {

        String request= "{\n" +
                "    \"fname\":\"Tiago\",\n" +
                "\n\"lname\": \"Oliveira\",\n" +
                "\n\"address\": \"\",\n" +
                "\n\"phone\": \"919191785\"\n" +
                "}";

        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = userService.registerManager(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_MANAGER_ADDRESS));
    }

    @Test
    public void whenRegisterManagerWithNoPhoneReturnForbidden() throws JsonProcessingException {

        String request= "{\n" +
                "    \"fname\":\"Tiago\",\n" +
                "\n\"lname\": \"Oliveira\",\n" +
                "\n\"address\": \"Rua fixe\",\n" +
                "\n\"phone\": \"\"\n" +
                "}";

        JsonNode payload = objectMapper.readTree(request);

        ResponseEntity<Object> r = userService.registerManager(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_MANAGER_PHONE));
    }

    @Test
    public void whenRegisterManagerReturnCreated() throws JsonProcessingException {

        String request= "{\n" +
                "    \"fname\":\"Tiago\",\n" +
                "\n\"lname\": \"Oliveira\",\n" +
                "\n\"address\": \"Rua fixe\",\n" +
                "\n\"phone\": \"919191785\"\n" +
                "}";

        JsonNode payload = objectMapper.readTree(request);

        Manager m1 = new Manager("Tiago","Oliveira","Rua fixe","919191785");

        given(managerRepository.save(m1)).willReturn(m1);

        ResponseEntity<Object> r = userService.registerManager(payload);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.CREATED)));

        assertThat(r.getBody(), is(HttpResponses.MANAGER_SAVED));
    }

    @Test
    public void whenGetManagerInfo_thenReturnCreated() {

        Manager m1 = new Manager("Tiago","Oliveira","Rua fixe","919191785");

        given(managerRepository.findByManagerId(1L)).willReturn(m1);

        ResponseEntity<Object> r = userService.getManager(1L);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.OK)));

    }

    @Test
    public void whenGetInvalidManagerInfo_thenReturnInvalidManager() {

        given(managerRepository.findByManagerId(2L)).willReturn(null);

        ResponseEntity<Object> r = userService.getManager(1L);

        assertThat(r.getStatusCode(), is(samePropertyValuesAs(HttpStatus.FORBIDDEN)));

        assertThat(r.getBody(), is(HttpResponses.INVALID_MANAGER));

    }
}