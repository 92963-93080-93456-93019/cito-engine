package ua.tqs.cito.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ProductSalesDTO implements Comparable<ProductSalesDTO> {

    private Product product;
    private Long salesQuantity;

    public ProductSalesDTO(Product product, Long salesQuantity) {
        this.product = product;
        this.salesQuantity = salesQuantity;
    }


    @Override
    public int compareTo(ProductSalesDTO o) {
        return this.salesQuantity < o.salesQuantity ? 1 : -1;
    }
}
