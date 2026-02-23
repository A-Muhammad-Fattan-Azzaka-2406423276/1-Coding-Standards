package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getProductQuantity()<0){
            throw new IllegalArgumentException("Product quantity cannot be less than 0");
        }
        UUID uuid = UUID.randomUUID();
        product.setProductId(uuid.toString());
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        for (Product product : productData) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product update(String id, Product updatedProductData) {
        if (updatedProductData.getProductQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be less  than 0");
        }

        Product existingProduct = findById(id);

        if (existingProduct != null) {
            existingProduct.setProductName(updatedProductData.getProductName());
            existingProduct.setProductQuantity(updatedProductData.getProductQuantity());

            return existingProduct;
        }
        return null;
    }

    public Product delete(String id) {
        Product product = findById(id);

        if (product != null) {
            productData.remove(product);
            return product;
        }
        return null;
    }
}
