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


}
