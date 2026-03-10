package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Order {
    private String id;
    private List<Product> products;
    private Long orderTime;
    private String author;
    private String status;

    public Order(String id, List<Product> products, Long orderTime, String author) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one product");
        }
        this.id = id;
        this.products = products;
        this.orderTime = orderTime;
        this.author = author;
        this.status = "WAITING_PAYMENT";
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this(id, products, orderTime, author);
        List<String> validStatuses = List.of("WAITING_PAYMENT", "FAILED", "CANCELLED", "SUCCESS");
        if (validStatuses.contains(status)) {
            this.status = status;
        }
    }

    public void setStatus(String status) {
        List<String> validStatuses = List.of("WAITING_PAYMENT", "FAILED", "CANCELLED", "SUCCESS");
        if (validStatuses.contains(status)) {
            this.status = status;
        }
    }
}