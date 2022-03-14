package fiit.mtaa.mtaa_backend.services;

import fiit.mtaa.mtaa_backend.models.Order;
import fiit.mtaa.mtaa_backend.models.OrderMeal;
import fiit.mtaa.mtaa_backend.repositories.OrderMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderMealService {
    @Autowired
    private OrderMealRepository orderMealRepository;

    public List<OrderMeal> getAllOrderMeals() {
        return orderMealRepository.findAll();
    }

    public Optional<OrderMeal> getOrderMealById(Long id) {
        return orderMealRepository.findById(id);
    }

    public OrderMeal saveOrderMeal(OrderMeal orderMeal) {
        return orderMealRepository.save(orderMeal);
    }

    public void deleteOrderMeal(Long id) {
        orderMealRepository.deleteById(id);
    }
}
