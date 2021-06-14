package ua.tqs.cito.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import ua.tqs.cito.CitoApplication;
import ua.tqs.cito.model.Rider;
import ua.tqs.cito.repository.RiderRepository;
import ua.tqs.cito.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CitoApplication.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderServiceUnitTest {

    @Mock
    private RiderRepository riderRepository;

    @InjectMocks
    private OrderService orderService;


    @Test
    public void MatchingRiderTest(){

        Rider r1 = new Rider(1L,"Dinis","Cruz","912223334","Mercedes","00-00-00");
        Rider r2 = new Rider(2L,"Tiago","Oliveira","912223333","Ford","11-11-11");

        r1.setLatitutde(51.0);
        r1.setLongitude(51.0);
        r2.setLatitutde(52.0);
        r2.setLongitude(52.0);

        List<Rider> riders = new ArrayList<>();
        riders.add(r1);
        riders.add(r2);

        given( riderRepository.findAll()).willReturn(riders);

        Rider response = orderService.matchRider(50.0,50.0);

        assertThat(response).isEqualTo(r1);
    }

    @Test
    public void MatchingRiderTest_WithNoriders(){

        List<Rider> riders = new ArrayList<>();

        given( riderRepository.findAll()).willReturn(riders);

        Rider response = orderService.matchRider(50.0,50.0);

        assertThat(response).isEqualTo(null);
    }


    @Test
    public void CalculateDistanceTest(){

        Double response = orderService.calculateDistance(50.0,50.0,51,51);
        Double response1 = orderService.calculateDistance(50.0,50.0,52,52);

        assertThat(response ).isLessThan(response1);
    }
}
