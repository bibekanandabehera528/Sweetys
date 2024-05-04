package com.product.repositories;

import com.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    @Query(value = "select * from products where is_live = true",nativeQuery = true)
    List<Product> getAllLiveProducts();

    @Query(value = "select * from products where title like %:key%",nativeQuery = true)
    List<Product> getAllProductsByTitle(@Param("key") String key);

    @Query(value = "select * from products where category_id =:categoryId",nativeQuery = true)
    List<Product> getProductsWithCategoryId(@Param("categoryId") String categoryId);
}
