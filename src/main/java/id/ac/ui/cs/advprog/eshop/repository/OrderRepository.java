package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private List<Order> orders = new ArrayList<>();

    public Order save(Order order) {
        int index = -1;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(order.getId())) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            orders.set(index, order);
        } else {
            orders.add(order);
        }
        return order;
    }

    public Order findById(String id) {
        for (Order order : orders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public List<Order> findAllByAuthor(String author) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getAuthor().equals(author)) {
                result.add(order);
            }
        }
        return result;
    }
}