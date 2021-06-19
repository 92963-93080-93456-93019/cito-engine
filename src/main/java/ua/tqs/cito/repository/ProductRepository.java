package ua.tqs.cito.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.tqs.cito.model.App;
import ua.tqs.cito.model.Product;
import ua.tqs.cito.model.ProductSalesDTO;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findById(Long l);
    public List<Product> findAll();
    public List<Product> findByApp(App l);
    public List<Product> findByNameLikeAndApp(String likeName, App app);
    @Query("SELECT new ua.tqs.cito.model.ProductSalesDTO(p, sum(pi.quantity)) FROM Product p JOIN ProductListItem pi ON p=pi.product WHERE p.app = ?1 GROUP BY p.id")
    public List<ProductSalesDTO> findMostSoldProductsOfApp(@Param("app_id") App app);

}
