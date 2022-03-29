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
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Object getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<JSONObject> result = new ArrayList<>();
        for (Order o: orders) {
            JSONObject jo = new JSONObject();
            jo.put("id", o.getId());
            jo.put("price", o.getPrice());
            jo.put("user", o.getUser().getLogin());
            jo.put("pay_by_cash", o.isPay_by_cash());
            jo.put("done", o.getDone());
            result.add(jo);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/addOrder")
    public JSONObject addOrder(@RequestParam List<Long> mealsId, @RequestParam Long userId, @RequestParam boolean pay_by_cash) {
        User user = userService.getUserById(userId);
        List<Meal> order_meals = new ArrayList<>();
        for (Long id : mealsId){
            order_meals.add(mealService.getMealById(id));
        }
        Order order = new Order();
        order.setUser(user);
        order.setPay_by_cash(pay_by_cash);
        Integer price = 0;
        for (Meal meal : order_meals){
            price += meal.getPrice();
        }
        order.setPrice(price);
        Order result = orderService.saveOrder(order);
        for (Meal meal : order_meals){
            orderMealService.saveOrderMeal(new OrderMeal().setDependencies(meal, result));
        }

        JSONObject jo = new JSONObject();
        jo.put("price", result.getPrice());
        jo.put("user", result.getUser().getLogin());


        return jo;
    }

    @DeleteMapping("/delOrder/{id}")
    public boolean delOrder(@PathVariable(value = "id") Long orderID) {
        orderService.deleteOrder(orderID);
        return true;
    }

    @GetMapping("/getOrder/{id}")
    public Object getOrderById(@PathVariable(value = "id") Long orderID)
            throws ResourceNotFoundException {
        try {
            Order order = orderService.getOrderById(orderID);
            JSONObject jo = new JSONObject();
            jo.put("id", order.getId());
            jo.put("price", order.getPrice());
            jo.put("user", order.getUser().getLogin());
            jo.put("pay_by_cash", order.isPay_by_cash());
            jo.put("done", order.getDone());
            return new ResponseEntity<>(jo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/setOrderDone/{id}")
    public ResponseEntity<JSONObject> setOrderDone(@PathVariable(value = "id") Long orderID) {
        try {
            Order edit_order = orderService.getOrderById(orderID);
            edit_order.setDone(true);
            edit_order = orderService.saveOrder(edit_order);

            JSONObject jo = new JSONObject();
            jo.put("id", edit_order.getId());
            jo.put("price", edit_order.getPrice());
            jo.put("user", edit_order.getUser().getLogin());
            jo.put("pay_by_cash", edit_order.isPay_by_cash());
            jo.put("done", edit_order.getDone());
            return new ResponseEntity<>(jo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUndoneOrders")
    public Object getUndoneOrders() {
        try {
            List<Order> orders = orderService.getUndoneOrders();
            List<JSONObject> result = new ArrayList<>();
            for (Order o: orders) {
                JSONObject jo = new JSONObject();
                jo.put("id", o.getId());
                jo.put("price", o.getPrice());
                jo.put("user", o.getUser().getLogin());
                jo.put("pay_by_cash", o.isPay_by_cash());
                jo.put("done", o.getDone());
                result.add(jo);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
