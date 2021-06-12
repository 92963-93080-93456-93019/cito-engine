package ua.tqs.cito.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
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

	public Long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
