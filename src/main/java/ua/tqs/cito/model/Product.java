package ua.tqs.cito.model;

import javax.persistence.*;
import lombok.*;



@Entity
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Product {

	public Product(String name, String category, String description, App app, Double price, String image) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.setApp(app);
		this.price = price;
		this.image = image;
	}

	public Product(Long id,String name, String category, String description, App app, Double price, String image) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.setApp(app);
		this.price = price;
		this.image = image;
		this.id=id;
	}

	public Product() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "appid") // An App has many products (foreign key)
	private App app;
	private String category;
	private String description;
	private Double price;
	
	@Lob
	private String image;

}
