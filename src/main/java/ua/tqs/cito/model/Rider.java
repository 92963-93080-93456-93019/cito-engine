package ua.tqs.cito.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long riderId;

    private String fname;
    private String lname;
    private String fnumber;
    private int reputation;
    @ElementCollection
    private List<Integer> reps;
    private String vehicleName;
    private String vehicleLicense;

    public Rider(String fname, String lname, String fnumber,String vehicleName,String vehicleLicense) {
        this.fname=fname;
        this.lname=lname;
        this.fnumber=fnumber;
        this.reputation=0;
        this.vehicleName=vehicleName;
        this.vehicleLicense=vehicleLicense;
        this.reps = new ArrayList<>();
    }

    public Rider(Long riderId,String fname, String lname, String fnumber,String vehicleName,String vehicleLicense) {
        this.fname=fname;
        this.lname=lname;
        this.fnumber=fnumber;
        this.reputation=0;
        this.reps = new ArrayList<>();
        this.riderId=riderId;
        this.vehicleLicense=vehicleLicense;
        this.vehicleName=vehicleName;
    }

    public void addRep(Integer i){
        this.reps.add(i);
        int total = 0;
        for(Integer rep:this.reps){
            total+=rep;
        }
        this.reputation=total/reps.size();
    }


}
