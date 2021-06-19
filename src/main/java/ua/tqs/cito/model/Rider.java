package ua.tqs.cito.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long riderId;
    private String fname;
    private String lname;
    private String phoneNumber;
    private int reputation;
    @ElementCollection
    private List<Integer> reps;
    private String vehicleName;
    private String vehicleLicense;
    private Boolean ifAvailable=true;
    private Double latitude;
    private Double longitude;

    public Rider(String fname, String lname, String phoneNumber,String vehicleName,String vehicleLicense) {
        this.fname=fname;
        this.lname=lname;
        this.phoneNumber=phoneNumber;
        this.reputation=0;
        this.vehicleName=vehicleName;
        this.vehicleLicense=vehicleLicense;
        this.reps = new ArrayList<>();
        this.latitude=0.0;
        this.longitude=0.0;
    }

    public Rider(Long riderId,String fname, String lname, String phoneNumber,String vehicleName,String vehicleLicense) {
        this.fname=fname;
        this.lname=lname;
        this.phoneNumber=phoneNumber;
        this.reputation=0;
        this.reps = new ArrayList<>();
        this.riderId=riderId;
        this.vehicleLicense=vehicleLicense;
        this.vehicleName=vehicleName;
        this.latitude=0.0;
        this.longitude=0.0;
    }

    public Rider() {
    }

    public void addRep(Integer i){
        this.reps.add(i);
        int total = 0;
        for(Integer rep:this.reps){
            total+=rep;
        }
        this.reputation=total/reps.size();

    }

    public Long getRiderId() {
        return riderId;
    }

    public void setIfAvailable(Boolean ifAvailable) {
        this.ifAvailable = ifAvailable;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
