package fiit.mtaa.mtaa_backend.repositories;

import fiit.mtaa.mtaa_backend.models.OrderMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMealRepository extends JpaRepository<OrderMeal, Long> {

}
