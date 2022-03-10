package fiit.mtaa.mtaa_backend.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;

@Data
@Table(name="meals")
@Entity
public class Meal {

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="name", unique=true, nullable = false)
    private String name;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="price", nullable = false)
    private Integer price;

//    @Column(name="photo")
//    @Lob
//    private byte[] photo;

    public String getName() {
        return name;
    }
}
