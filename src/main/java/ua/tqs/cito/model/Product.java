package ua.tqs.cito.model;

import javax.persistence.*;

import com.sun.istack.NotNull;
import lombok.*;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "appId") // An App has many products (foreign key)
	private App app;
	private String category;
	private String description;
	private Double price;
	
	@Lob
	private String image;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
