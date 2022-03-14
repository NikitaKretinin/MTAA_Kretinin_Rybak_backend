package fiit.mtaa.mtaa_backend.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name="users")
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="login", unique=true, nullable = false)
    private String login;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="user_role", nullable = false)
    private String user_role;

    public String getUser_role() {
        return user_role;
    }
}
