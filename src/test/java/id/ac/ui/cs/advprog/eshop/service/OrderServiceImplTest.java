package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products1 = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products1.add(product1);

        orders = new ArrayList<>();
        orders.add(new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products1, 1708560000L, "Safira Sudrajat"));
        orders.add(new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products1, 1708570000L, "Safira Sudrajat"));
    }

    @Test
    void testCreateOrder() {
        Order order = orders.get(0);
        when(orderRepository.findById(order.getId())).thenReturn(null);
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.createOrder(order);
        verify(orderRepository, times(1)).save(order);
        assertEquals(order, result);
    }

    @Test
    void testCreateOrderAlreadyExists() {
        Order order = orders.get(0);
        when(orderRepository.findById(order.getId())).thenReturn(order);

        assertThrows(IllegalStateException.class, () -> orderService.createOrder(order));
        verify(orderRepository, times(0)).save(order);
    }

    @Test
    void testUpdateStatus() {
        Order order = orders.get(0);
        when(orderRepository.findById(order.getId())).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Order order = orders.get(0);
        when(orderRepository.findById(order.getId())).thenReturn(order);

        assertThrows(IllegalArgumentException.class,
                () -> orderService.updateStatus(order.getId(), "INVALID_STATUS"));
    }

    @Test
    void testUpdateStatusOrderNotFound() {
        when(orderRepository.findById("nonexistent-id")).thenReturn(null);

        assertThrows(NoSuchElementException.class,
                () -> orderService.updateStatus("nonexistent-id", OrderStatus.SUCCESS.getValue()));
    }

    @Test
    void testFindByIdFound() {
        Order order = orders.get(0);
        when(orderRepository.findById(order.getId())).thenReturn(order);

        Order result = orderService.findById(order.getId());
        assertEquals(order, result);
    }

    @Test
    void testFindByIdNotFound() {
        when(orderRepository.findById("nonexistent-id")).thenReturn(null);

        assertNull(orderService.findById("nonexistent-id"));
    }

    @Test
    void testFindAllByAuthorFound() {
        when(orderRepository.findAllByAuthor("Safira Sudrajat")).thenReturn(orders);

        List<Order> result = orderService.findAllByAuthor("Safira Sudrajat");
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllByAuthorLowercase() {
        when(orderRepository.findAllByAuthor("safira sudrajat")).thenReturn(new ArrayList<>());

        List<Order> result = orderService.findAllByAuthor("safira sudrajat");
        assertTrue(result.isEmpty());
    }
}
