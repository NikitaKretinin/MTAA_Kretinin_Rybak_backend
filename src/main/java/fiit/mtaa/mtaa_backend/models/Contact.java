package fiit.mtaa.mtaa_backend.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name="contacts")
@Entity
public class Contact {

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="address", nullable = false)
    private String address;

    @Column(name="phone")
    private String phone;
}
