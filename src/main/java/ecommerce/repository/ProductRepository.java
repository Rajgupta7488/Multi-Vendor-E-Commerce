package ecommerce.repository;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByIsActiveTrue();

    List<Product> findByCategory(String category);

    List<Product> findByCategoryAndIsActiveTrue(String category);

    List<Product> findByVendor(User vendor);

    List<Product> findByVendorAndIsActiveTrue(User vendor);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE (p.name LIKE %:keyword% OR p.description LIKE %:keyword%) AND p.isActive = true")
    List<Product> findByKeywordAndIsActiveTrue(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.isActive = true")
    List<String> findDistinctCategories();

    List<Product> findByStockQuantityGreaterThan(Integer quantity);

    List<Product> findByStockQuantityLessThanEqual(Integer quantity);
}