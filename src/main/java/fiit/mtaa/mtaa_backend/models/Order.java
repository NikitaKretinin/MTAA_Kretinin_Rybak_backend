package fiit.mtaa.mtaa_backend.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy="order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderMeal> orderMeals = new ArrayList<>();

    @Column(name="pay_by_cash", nullable = false)
    private boolean pay_by_cash;

    @Column(name="done", nullable = false)
    private Boolean done = false;
}
