package fiit.mtaa.mtaa_backend.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Table(name="orders")
@Entity
public class Order {

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="user_id", nullable = false)
    private Integer user_id;

    @Column(name="meals_id", nullable = false)
    @Type(type = "fiit.mtaa.mtaa_backend.artifacts_data.CustomIntegerDataType")
    private Integer[] meals_id;

    @Column(name="price", nullable = false)
    private Integer price;

    @Column(name="done", nullable = false)
    private Boolean done = false;

    public Integer[] getMeals() {
        return meals_id;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
