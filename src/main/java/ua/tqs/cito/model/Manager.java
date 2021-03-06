package ua.tqs.cito.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@ToString
@Getter
@Setter
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long managerId;
    private String fname;
    private String lname;
    private String phone;
    private String address;
    @ManyToOne
    @JoinColumn(name = "appId") // An App has many managers (foreign key)
    private App app;
	public Manager(Long managerId, String fname, String lname, String phone, String address) {
		this.managerId = managerId;
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
		this.address = address;
		this.app=null;
	}

	public Manager(String fname, String lname, String phone, String address) {
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
		this.address = address;
		this.app=null;
	}

	public Manager() {
	}


}
