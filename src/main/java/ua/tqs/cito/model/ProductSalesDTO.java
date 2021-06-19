package ua.tqs.cito.model;

public class ProductSalesDTO  implements Comparable<ProductSalesDTO>{

Product product;
Long salesQuantity;

    public ProductSalesDTO(Product product, Long salesQuantity){
        this.product = product;
        this.salesQuantity = salesQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public Long getSalesQuantity() {
        return salesQuantity;
    }

    @Override
    public int compareTo(ProductSalesDTO o) {
        return this.salesQuantity < o.salesQuantity ? 1 : -1;
    }
}
