package fiit.mtaa.mtaa_backend.repositories;

import fiit.mtaa.mtaa_backend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
