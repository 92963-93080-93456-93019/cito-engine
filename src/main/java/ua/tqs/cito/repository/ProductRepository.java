package ua.tqs.cito.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tqs.cito.model.App;
import ua.tqs.cito.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findById(Long l);
    public List<Product> findAll();
    public List<Product> findByApp(App l);
    public List<Product> findByNameLikeAndApp(String likeName, App app);

}
