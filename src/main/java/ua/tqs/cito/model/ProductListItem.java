package ua.tqs.cito.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@ToString
@Getter
@Setter
public class ProductListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productsListItemId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private Product product;
    private int quantity;

    public ProductListItem(Product product, int quantity) {
        this.product=product;
        this.quantity=quantity;
    }

    public ProductListItem() {
    }

}
