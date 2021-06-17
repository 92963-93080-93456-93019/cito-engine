package ua.tqs.cito.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
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

	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
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
	public App getApp() {
		return app;
	}
	public void setApp(App app) {
		this.app = app;
	}

}
