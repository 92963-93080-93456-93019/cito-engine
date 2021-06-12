package ua.tqs.cito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tqs.cito.model.Consumer;
import ua.tqs.cito.model.Order;

import java.util.List;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    public List<Consumer> findAll();
    public Consumer findByConsumerId(Long id);
}