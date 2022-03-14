package fiit.mtaa.mtaa_backend.controllers;

import fiit.mtaa.mtaa_backend.models.Order;
import fiit.mtaa.mtaa_backend.models.Meal;
import fiit.mtaa.mtaa_backend.services.MealService;
import fiit.mtaa.mtaa_backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MealService mealService;

    @GetMapping("/getOrders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/addOrder")
    public Order addOrder(@RequestBody Order order) {
        Integer price = 0;
        /*for (int i : order.getMeals()){
            Long id = (long) i;
            price += mealService.getMealById(id).get().getPrice();
        }*/
        order.setPrice(price);
        Order res = orderService.saveOrder(order);
        return res;
    }
}
