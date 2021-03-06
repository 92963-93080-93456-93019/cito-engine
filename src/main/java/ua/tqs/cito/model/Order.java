package ua.tqs.cito.model;

import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.tqs.cito.utils.OrderStatusEnum;



@Entity
@Table(name = "consumer_order")
@ToString
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<ProductListItem> productListItems; // An order has one productListItems

    private Double price;

    @ManyToOne
    @JoinColumn(name = "consumerId") // A Consumer has many orders (foreign key)
    private Consumer endConsumer;

    private OrderStatusEnum orderStatusEnum;

    @ManyToOne
    @JoinColumn(name = "riderId") // A rider  has many orders (foreign key)
    private Rider rider;

    @ManyToOne
    @JoinColumn(name = "appId") // An App has many orders (foreign key)
    private App app;

    private String address;


    private Double latitude;

    private Double longitude;

    public Order(List<ProductListItem> productListItems, Consumer endConsumer, OrderStatusEnum orderStatusEnum, App app, String address,Double latitude, Double longitude){

        this.productListItems=productListItems;
        this.endConsumer=endConsumer;
        this.orderStatusEnum = orderStatusEnum.PENDING;
        this.rider=null;
        this.app=app;
        this.price=0.0;
        for (ProductListItem p : productListItems) {
            this.price = this.price + p.getProduct().getPrice()*p.getQuantity();
        }
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
    }


    public Order(Long orderId, List<ProductListItem> productListItems, Consumer endConsumer, OrderStatusEnum orderStatusEnum, App app, String address, Double latitude, Double longitude){

        this.productListItems=productListItems;
        this.endConsumer=endConsumer;
        this.orderStatusEnum = orderStatusEnum.PENDING;
        this.rider=null;
        this.app=app;
        this.price=0.0;
        for (ProductListItem p : productListItems) {
            this.price = this.price + p.getProduct().getPrice()*p.getQuantity();
        }
        this.address=address;
        this.orderId=orderId;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public Order() {
    }


}
