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
public class App {

    public App(Long appid, Double tax, String name, String address, String schedule, String image) {
        this.appid = appid;
        this.tax = tax;
        this.name = name;
        this.address = address;
        this.schedule = schedule;
        this.image = image;
    }

	public App(Double tax, String name, String address, String schedule, String image) {
		this.tax = tax;
		this.name = name;
		this.address = address;
		this.schedule = schedule;
		this.image = image;
		System.out.println("delete this print");
	}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appid;
    private Double tax;
    private String name;
    private String address;
    private String schedule;

    @Lob
    private String image;

	public Long getAppid() {
		return appid;
	}

	public void setAppid(Long appid) {
		this.appid = appid;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
