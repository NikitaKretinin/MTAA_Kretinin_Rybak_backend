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

    @Column(name="done", nullable = false)
    private Boolean done = false;

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setUser(User user){ this.user = user; }

    public Integer getPrice() {
        return price;
    }

    public User getUser() {
        return user;
    }
}
