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
public class ProductListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productsListItemId;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    private int quantity;

    public ProductListItem(Product product, int quantity) {
        this.product=product;
        this.quantity=quantity;
    }

}
