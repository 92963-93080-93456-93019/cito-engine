package ua.tqs.cito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tqs.cito.model.App;


@Repository
public interface AppRepository extends JpaRepository<App,String> {
    public App findByAppid(Long appid);
}
