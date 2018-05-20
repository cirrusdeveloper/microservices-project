package org.microservices.project.stockserver.repository;

import org.microservices.project.stockserver.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository interface for persistence of product categories.
 * @author mcaleerj
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findById(Long categoryId);
}
