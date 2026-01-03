package com.wishit.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.wishit.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	List<Product> findbyVendorId(Long vendorId);

    @Query("""
        SELECT p
        FROM Product p
	    JOIN p.category c
        WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :categoryName, '%'))
	    """)
    List<Product> findProductsByCategoryName(@Param("categoryName") String categoryName);

}
