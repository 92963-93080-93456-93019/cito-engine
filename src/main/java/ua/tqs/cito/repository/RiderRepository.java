package ua.tqs.cito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tqs.cito.model.Rider;

import java.util.List;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    public List<Rider> findAll();
    public Rider findByRiderId(Long id);
}