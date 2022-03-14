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

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, referencedColumnName="id")
    private User user;

    @Column(name="price", nullable = false)
    private Integer price;

    @Column(name="done", nullable = false)
    private Boolean done = false;

    public void setPrice(Integer price) {
        this.price = price;
    }
}
