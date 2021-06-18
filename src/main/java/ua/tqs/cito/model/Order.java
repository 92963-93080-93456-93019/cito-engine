package ua.tqs.cito.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.tqs.cito.utils.OrderStatusEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "consumer_order")
@Setter
@Getter
@ToString
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<ProductListItem> getProductListItems() {
        return productListItems;
    }

    public void setProductListItems(List<ProductListItem> productListItems) {
        this.productListItems = productListItems;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Consumer getEndConsumer() {
        return endConsumer;
    }

    public void setEndConsumer(Consumer endConsumer) {
        this.endConsumer = endConsumer;
    }

    public OrderStatusEnum getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public void setOrderStatusEnum(OrderStatusEnum orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
