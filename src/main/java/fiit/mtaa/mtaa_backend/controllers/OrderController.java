package fiit.mtaa.mtaa_backend.controllers;

import fiit.mtaa.mtaa_backend.artifacts_data.TokenManager;
import fiit.mtaa.mtaa_backend.models.Order;
import fiit.mtaa.mtaa_backend.models.Meal;
import fiit.mtaa.mtaa_backend.models.OrderMeal;
import fiit.mtaa.mtaa_backend.models.User;
import fiit.mtaa.mtaa_backend.services.*;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public Object getAllOrders(@RequestHeader(value="Authorization") String token) {
        if (TokenManager.validToken(token, "manager")) {
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
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/addOrder")
    public Object addOrder(@RequestParam List<Long> mealsId,
                               @RequestHeader(value="Authorization") String token,
                               @RequestParam boolean pay_by_cash) {
        if (TokenManager.validToken(token, "guest")) {
            User user = userService.getUserById(TokenManager.getIdByToken(token));
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
            jo.put("pay_by_cash", pay_by_cash);

            return new ResponseEntity<>(jo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/delOrder/{id}")
    public Object delOrder(@PathVariable(value = "id") Long orderID,
                           @RequestHeader(value="Authorization") String token) {
        if (TokenManager.validToken(token, "manager")) {
            orderService.deleteOrder(orderID);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getOrder/{id}")
    public Object getOrderById(@PathVariable(value = "id") Long orderID,
                               @RequestHeader(value="Authorization") String token)
            throws ResourceNotFoundException {
        try {
            if (TokenManager.validToken(token, "manager")) {
                Order order = orderService.getOrderById(orderID);
                JSONObject jo = new JSONObject();
                jo.put("id", order.getId());
                jo.put("price", order.getPrice());
                jo.put("user", order.getUser().getLogin());
                jo.put("pay_by_cash", order.isPay_by_cash());
                jo.put("done", order.getDone());
                return new ResponseEntity<>(jo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/setOrderDone/{id}")
    public Object setOrderDone(@PathVariable(value = "id") Long orderID,
                               @RequestHeader(value="Authorization") String token) {
        try {
            if (TokenManager.validToken(token, "manager")) {
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
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUndoneOrders")
    public Object getUndoneOrders(@RequestHeader(value="Authorization") String token) {
        try {
            if (TokenManager.validToken(token, "manager")) {
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
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
