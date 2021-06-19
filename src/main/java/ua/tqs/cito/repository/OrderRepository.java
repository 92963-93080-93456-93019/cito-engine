package ua.tqs.cito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.tqs.cito.model.*;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findAll();
    public List<Order> findOrdersByApp(App app);
    public Order findByOrderId(Long id);
    public List<Order> findOrdersByEndConsumer(Consumer c);
    public List<Order> findOrdersByRider(Rider rider);
    @Query("SELECT DISTINCT o.endConsumer FROM Order o WHERE o.app = ?1")
    public List<Consumer> findNumberOfDifferentClients(@Param("app_id") App app);

}
