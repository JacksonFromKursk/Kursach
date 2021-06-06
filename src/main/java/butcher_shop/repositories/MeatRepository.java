package butcher_shop.repositories;

import butcher_shop.models.Meat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeatRepository extends JpaRepository<Meat, Integer> {
    List<Meat> findAllBySort(String sort);
    Meat findByName(String name);
}
