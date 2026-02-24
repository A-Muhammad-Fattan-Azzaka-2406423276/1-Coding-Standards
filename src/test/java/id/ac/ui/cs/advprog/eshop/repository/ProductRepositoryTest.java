package id.ac.ui.cs.advprog.eshop.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Iterator;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {

    }
    @Test
    void testCreateAndFind(){
        Product product=new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator=productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct=productIterator.next();
        assertEquals(product.getProductId(),savedProduct.getProductId());
        assertEquals(product.getProductName(),savedProduct.getProductName());
        assertEquals(product.getProductQuantity(),savedProduct.getProductQuantity());
    }

    @Test
    void testCreateNegativeQuantityProduct(){
        Product product=new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(-10);

        assertThrows(IllegalArgumentException.class, ()->{
            productRepository.create(product);
        });
        String idProduct = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        assertNull(productRepository.findById(idProduct));
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProductSuccess() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        String id = product.getProductId();

        Product result = productRepository.delete(id);

        assertNotNull(result);
        assertEquals(id, result.getProductId());
        assertNull(productRepository.findById(id));
    }

    @Test
    void testUpdateProductSuccess() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Sampo Cap Budi");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update(product.getProductId(),updatedProduct);

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        assertEquals("Sampo Cap Budi", result.getProductName());
        assertEquals(200, result.getProductQuantity());

        Product savedProduct = productRepository.findById(product.getProductId());
        assertEquals("Sampo Cap Budi", savedProduct.getProductName());
        assertEquals(200, savedProduct.getProductQuantity());
    }

    @Test
    void testUpdateProductNegativeQuantity() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Sampo Cap Budi");
        updatedProduct.setProductQuantity(-50);

        assertThrows(IllegalArgumentException.class, () -> {
            productRepository.update(product.getProductId(), updatedProduct);
        });

        Product savedProduct = productRepository.findById(product.getProductId());
        assertEquals("Sampo Cap Bambang", savedProduct.getProductName());
        assertEquals(100, savedProduct.getProductQuantity());
    }

    @Test
    void testDeleteProduct_NotFound() {
        assertNull(productRepository.delete("00191920"));
    }

    @Test
    void testUpdateProduct_NotFound() {
        Product updatedProduct = new Product();
        assertNull(productRepository.update("00191920", updatedProduct));
    }
}
