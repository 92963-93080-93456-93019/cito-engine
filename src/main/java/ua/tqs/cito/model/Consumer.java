package ua.tqs.cito.model;

import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@ToString
@Getter
@Setter
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long consumerId;
    @ManyToOne
    @JoinColumn(name = "appId") // An App has many consumers (foreign key)
    private App app;
    private String fname;
    private String lname;
    private String phone;
    private String address;
    @OneToMany
    @JoinColumn(name = "consumer") // A Consumer has many orders (foreign key)
    private List<Order> orders;


    public Consumer(String fname, String lname,String phone, String address, App app){
        this.fname=fname;
        this.lname=lname;
        this.phone=phone;
        this.address=address;
        this.app=app;
    }

    public Consumer(Long consumerId, String fname, String lname, String phone, String address, App app){
        this.fname=fname;
        this.lname=lname;
        this.phone=phone;
        this.address=address;
        this.app=app;
        this.consumerId = consumerId;
    }

    public Consumer() {
    }

}
