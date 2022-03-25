package fiit.mtaa.mtaa_backend.services;

import fiit.mtaa.mtaa_backend.artifacts_data.HibernateUtil;
import fiit.mtaa.mtaa_backend.models.Meal;
import fiit.mtaa.mtaa_backend.models.Order;
import fiit.mtaa.mtaa_backend.models.User;
import fiit.mtaa.mtaa_backend.repositories.MealRepository;
import fiit.mtaa.mtaa_backend.repositories.OrderRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    public List<Order> getUndoneOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("SELECT a FROM Order a WHERE a.done=:isdone", Order.class).setParameter("isdone", false).getResultList();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return null;
    }
}
