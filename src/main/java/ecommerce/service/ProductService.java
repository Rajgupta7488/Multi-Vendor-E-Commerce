package ecommerce.service;

import ecommerce.dto.ProductResponse;
import ecommerce.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getActiveProducts();
    ProductResponse getProductById(Long id);
    List<ProductResponse> getProductsByCategory(Long categoryId);
    List<ProductResponse> searchProducts(String keyword);
    List<ProductResponse> getAvailableProducts();
    Product findProductEntityById(Long id);
    void updateStock(Long productId, Integer quantity);
    boolean isProductAvailable(Long productId, Integer requestedQuantity);
}