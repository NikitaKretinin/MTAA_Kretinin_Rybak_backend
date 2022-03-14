package fiit.mtaa.mtaa_backend.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name="orders_meals")
@Entity
public class OrderMeal {

    @Id
    @GeneratedValue
    @Column(name="id", nullable=false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id", nullable=false, referencedColumnName="id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="meal_id", nullable=false, referencedColumnName="id")
    private Meal meal;

    public OrderMeal setDependencies(Meal meal, Order order){
        this.meal = meal;
        this.order = order;
        return this;
    }
}
