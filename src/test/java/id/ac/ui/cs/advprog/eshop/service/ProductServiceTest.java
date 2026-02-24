package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Spy
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateProduct() {
        Product savedProduct = productService.create(product);

        assertNotNull(savedProduct.getProductId());
        assertEquals("Sampo Cap Bambang", savedProduct.getProductName());
        assertEquals(100, savedProduct.getProductQuantity());

        List<Product> productList = productService.findAll();
        assertEquals(1, productList.size());
    }

    @Test
    void testFindAll() {
        productService.create(product);

        Product product2 = new Product();
        product2.setProductName("Sampo Cap Budi");
        product2.setProductQuantity(50);
        productService.create(product2);

        List<Product> productList = productService.findAll();

        assertEquals(2, productList.size());
    }

    @Test
    void testFindById() {
        Product savedProduct = productService.create(product);

        Product foundProduct = productService.findById(savedProduct.getProductId());

        assertNotNull(foundProduct);
        assertEquals(savedProduct.getProductId(), foundProduct.getProductId());
        assertEquals(savedProduct.getProductName(), foundProduct.getProductName());
    }

    @Test
    void testUpdate() {
        Product savedProduct = productService.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(savedProduct.getProductId());
        updatedProduct.setProductName("Kecap Cap Bango");
        updatedProduct.setProductQuantity(20);

        productService.update(updatedProduct);

        Product foundProduct = productService.findById(savedProduct.getProductId());
        assertEquals("Kecap Cap Bango", foundProduct.getProductName());
        assertEquals(20, foundProduct.getProductQuantity());
    }

    @Test
    void testDelete() {
        Product savedProduct = productService.create(product);
        assertFalse(productService.findAll().isEmpty());

        productService.delete(savedProduct.getProductId());

        assertTrue(productService.findAll().isEmpty());
    }
}