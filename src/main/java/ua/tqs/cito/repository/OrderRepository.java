package ua.tqs.cito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tqs.cito.model.Consumer;
import ua.tqs.cito.model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findAll();

    public Order findByOrderId(Long id);
    public List<Order> findOrdersByEndConsumer(Consumer c);
}
