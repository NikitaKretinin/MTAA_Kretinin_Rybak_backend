package fiit.mtaa.mtaa_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fiit.mtaa.mtaa_backend.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
