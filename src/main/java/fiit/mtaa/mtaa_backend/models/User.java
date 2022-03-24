package fiit.mtaa.mtaa_backend.models;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Table(name="users")
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    private Long id;

    @NaturalId
    @Column(name="login", unique=true, nullable = false)
    private String login;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="user_role", nullable = false)
    private String user_role = "guest";

    @ManyToOne
    @JoinColumn(name="contact_id", referencedColumnName="id")
    private Contact contact = null;
}
