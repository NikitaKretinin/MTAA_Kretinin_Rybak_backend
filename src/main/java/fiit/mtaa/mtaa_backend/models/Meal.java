package fiit.mtaa.mtaa_backend.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name="photo")
    private byte[] photo;

    @OneToMany(mappedBy="meal", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderMeal> orderMeals = new ArrayList<>();

    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }
}
