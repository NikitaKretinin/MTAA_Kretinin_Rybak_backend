package fiit.mtaa.mtaa_backend.repositories;

import fiit.mtaa.mtaa_backend.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
