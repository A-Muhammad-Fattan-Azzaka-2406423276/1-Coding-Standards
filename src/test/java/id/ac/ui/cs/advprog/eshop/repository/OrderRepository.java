package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {
    private OrderRepository orderRepository;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();

        List<Product> products1 = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products1.add(product1);

        List<Product> products2 = new ArrayList<>();
        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        products2.add(product2);

        orders = new ArrayList<>();
        orders.add(new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products1, 1708560000L, "Safira Sudrajat"));
        orders.add(new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products2, 1708570000L, "Safira Sudrajat"));
        orders.add(new Order("e334ef40-9eff-4da8-9487-8ee697ecbf1e",
                products1, 1708580000L, "Bambang Sudrajat"));
    }

    @Test
    void testSaveCreate() {
        Order order = orders.get(0);
        Order result = orderRepository.save(order);
        assertEquals(order, result);
    }

    @Test
    void testSaveUpdate() {
        Order order = orders.get(0);
        orderRepository.save(order);
        order.setStatus(OrderStatus.SUCCESS.getValue());
        Order result = orderRepository.save(order);
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testFindByIdFound() {
        for (Order order : orders) {
            orderRepository.save(order);
        }
        Order result = orderRepository.findById(orders.get(0).getId());
        assertEquals(orders.get(0).getId(), result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(orderRepository.findById("nonexistent-id"));
    }

    @Test
    void testFindAllByAuthorFound() {
        for (Order order : orders) {
            orderRepository.save(order);
        }
        List<Order> result = orderRepository.findAllByAuthor("Safira Sudrajat");
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllByAuthorNotFoundLowercase() {
        for (Order order : orders) {
            orderRepository.save(order);
        }
        List<Order> result = orderRepository.findAllByAuthor("safira sudrajat");
        assertTrue(result.isEmpty());
    }
}public class OrderRepository {
}
