package fiit.mtaa.mtaa_backend.controllers;

import fiit.mtaa.mtaa_backend.models.Order;
import fiit.mtaa.mtaa_backend.models.Meal;
import fiit.mtaa.mtaa_backend.models.OrderMeal;
import fiit.mtaa.mtaa_backend.models.User;
import fiit.mtaa.mtaa_backend.services.MealService;
import fiit.mtaa.mtaa_backend.services.OrderMealService;
import fiit.mtaa.mtaa_backend.services.OrderService;
import fiit.mtaa.mtaa_backend.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MealService mealService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderMealService orderMealService;

    @GetMapping("/getOrders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/addOrder")
    public JSONObject addOrder(@RequestParam List<Long> mealsId, @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        List<Meal> order_meals = new ArrayList<>();
        for (Long id : mealsId){
            order_meals.add(mealService.getMealById(id).get());
        }
        Order order = new Order();
        order.setUser(user);
        Integer price = 0;
        for (Meal meal : order_meals){
            price += meal.getPrice();
        }
        order.setPrice(price);
        Order result = orderService.saveOrder(order);
        for (Meal meal : order_meals){
            orderMealService.saveOrderMeal(new OrderMeal().setDependencies(meal, result));
//            result.addMeal(res);
        }

        JSONObject jo = new JSONObject();
        jo.put("price", result.getPrice());
        jo.put("user", result.getUser().getLogin());


        return jo;
    }

    @DeleteMapping("/delOrder/{id}")
    public boolean addOrder(@PathVariable(value = "id") Long orderID) {
        orderService.deleteOrder(orderID);
        return true;
    }
}
